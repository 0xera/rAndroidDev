package ru.aydarov.randroid.presentation.ui.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.databinding.NetworkStateBinding;
import ru.aydarov.randroid.databinding.RedditPostBinding;
import ru.aydarov.randroid.domain.util.PostHelper;
import ru.aydarov.randroid.domain.util.RedditUtils;
import ru.aydarov.randroid.presentation.common.INavigatorSource;

/**
 * @author Aydarov Askhar 2020
 */
public class PostAdapter extends PagedListAdapter<RedditPost, PostAdapter.RedditViewHolder> {
    private final Function0<Unit> mRetryFunc;
    private INavigatorSource mNavigatorSource;

    private NetworkState mNetworkState;
    private static final DiffUtil.ItemCallback<RedditPost> DIFF_CALLBACK = new DiffUtil.ItemCallback<RedditPost>() {
        @Override
        public boolean areItemsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return !TextUtils.isEmpty(oldItem.getTitle()) && !TextUtils.isEmpty(newItem.getTitle()) && oldItem.getTitle().equals(newItem.getTitle())
                    && !TextUtils.isEmpty(oldItem.getSelfText()) && !TextUtils.isEmpty(newItem.getSelfText()) && oldItem.getSelfText().equals(newItem.getSelfText());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RedditUtilsNet.PAYLOAD_DIFF, newItem);
            return bundle;
        }
    };

    public PostAdapter(Function0<Unit> retryFunc, INavigatorSource navigatorSource) {
        super(DIFF_CALLBACK);
        mRetryFunc = retryFunc;
        mNavigatorSource = navigatorSource;
    }

    public void setNetworkState(NetworkState networkState) {
        NetworkState prevState = mNetworkState;
        boolean extraRow = hasExtraRow();
        mNetworkState = networkState;
        if (extraRow != hasExtraRow()) {
            if (extraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (extraRow && prevState.getStatus() != networkState.getStatus()) {
            notifyItemChanged(getItemCount());
        }
    }

    @NonNull
    @Override
    public PostAdapter.RedditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.layout.reddit_post_item) {
            return new RedditPostViewHolder(RedditPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new NetworkStateHolder(NetworkStateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.RedditViewHolder holder, int position) {
        if (getItemViewType(position) == R.layout.reddit_post_item) {
            holder.bind(getItem(position));
        } else {
            holder.bind(mNetworkState);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.RedditViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            holder.bind(getItem(position));
        } else {
            onBindViewHolder(holder, position);
        }
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mNavigatorSource = null;
    }

    public void setNavigatorSource(INavigatorSource navigatorSource) {
        mNavigatorSource = navigatorSource;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.reddit_post_item;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    private boolean hasExtraRow() {
        return mNetworkState != null && mNetworkState.getStatus() != NetworkState.Status.SUCCESS;
    }


    abstract class RedditViewHolder extends RecyclerView.ViewHolder {
        RedditViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(RedditPost post) {
        }

        void bind(NetworkState networkState) {
        }
    }

    class RedditPostViewHolder extends RedditViewHolder {


        private final RedditPostBinding mBinding;
        private RedditPost mPost;
        private View.OnClickListener mOnClickMediaListener;
        private View.OnClickListener mOnClickShareListener;

        public RedditPostViewHolder(@NonNull RedditPostBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        void bind(RedditPost post) {
            mPost = post;
            binding();
        }

        private void binding() {
            if (mPost != null) {
                mBinding.ivImage.layout(0, 0, 0, 0);
                String urlPreview = RedditUtils.getUrlPreview(mPost);
                if (!TextUtils.isEmpty(urlPreview)) {
                    mBinding.ivImage.setVisibility(View.VISIBLE);
                    mBinding.ivImage.setOnClickListener(getMediaOpenListener());
                    LoaderSourceAdapter.loadImagePost(mBinding.ivImage, mBinding.pbProgress, urlPreview);
                } else {
                    mBinding.ivImage.setVisibility(View.GONE);
                }
                mBinding.ivShare.setOnClickListener(getShareListener());
                mBinding.tvTitle.setText(mPost.getTitle());
                mBinding.tvUsername.setText(mPost.getAuthor());
                PostHelper.setTime(mBinding.createdTime, mPost);
                PostHelper.setPostType(mBinding.tvType, mPost);
                PostHelper.highlightText(mBinding.tvTitle, mPost.getTitle(), mPost.getSearchQuery());
                PostHelper.setSelfText(mBinding.selfText, mPost);
            }
        }


        @NotNull
        private View.OnClickListener getShareListener() {
            if (mOnClickShareListener == null) {
                mOnClickShareListener = v -> shareLink(RedditUtilsNet.API_BASE_URI + mPost.getPermalink());
            }
            return mOnClickShareListener;
        }

        @NotNull
        private View.OnClickListener getMediaOpenListener() {
            if (mOnClickMediaListener == null) {
                mOnClickMediaListener = v -> {
                    if (mPost != null) {
                        Intent intent = PostHelper.getMediaIntent(mPost, v);
                        if (intent != null) {
                            if (!TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals(Intent.ACTION_VIEW)) {
                                v.getContext().startActivity(intent);
                            } else {
                                mNavigatorSource.navigateToSourceViewActivity(v, intent);
                            }
                        }
                    }
                };
            }
            return mOnClickMediaListener;
        }

        private void shareLink(String link) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, link);
            mNavigatorSource.onShare(intent);
        }

    }


    class NetworkStateHolder extends RedditViewHolder {
        private final ProgressBar mProgress;
        private final Button mRetry;
        private final TextView mTvError;

        NetworkStateHolder(@NonNull NetworkStateBinding binding) {
            super(binding.getRoot());
            mProgress = binding.progressBar;
            mRetry = binding.btnRetry;
            mRetry.setOnClickListener(v -> mRetryFunc.invoke());
            mTvError = binding.tvError;
        }

        @Override
        void bind(NetworkState networkState) {
            mProgress.setVisibility(getVisibility(networkState.getStatus() == NetworkState.Status.RUNNING));
            mRetry.setVisibility(getVisibility(networkState.getStatus() == NetworkState.Status.FAILED));
            mTvError.setVisibility(getVisibility(networkState.getMsg() != null));
            mTvError.setText(networkState.getMsg());


        }

        private int getVisibility(boolean value) {
            if (value)
                return View.VISIBLE;
            else
                return View.GONE;
        }


    }
}
