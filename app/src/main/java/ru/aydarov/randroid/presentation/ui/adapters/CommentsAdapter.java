package ru.aydarov.randroid.presentation.ui.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import ru.aydarov.randroid.data.model.CommentData;
import ru.aydarov.randroid.data.model.CommentList;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.databinding.RedditCommentBinding;
import ru.aydarov.randroid.domain.comments.NetworkCommentResult;
import ru.aydarov.randroid.presentation.common.INavigatorSource;
import ru.aydarov.randroid.presentation.util.RedditItemHelper;
import ru.noties.markwon.Markwon;

/**
 * @author Aydarov Askhar 2020
 */
public class CommentsAdapter extends ListAdapter<CommentList, CommentsAdapter.RedditViewHolder> {

    private static final DiffUtil.ItemCallback<CommentList> DIFF_CALLBACK = new DiffUtil.ItemCallback<CommentList>() {
        @Override
        public boolean areItemsTheSame(@NonNull CommentList oldItem, @NonNull CommentList newItem) {
            return oldItem.getData() != null && newItem.getData() != null &&
                    oldItem.getData().equals(newItem.getData());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CommentList oldItem, @NonNull CommentList newItem) {
            return oldItem.getData() != null && newItem.getData() != null
                    && oldItem.getData().equals(newItem.getData())
                    && oldItem.getData().getComments() != null && newItem.getData().getComments() != null
                    && oldItem.getData().getComments().containsAll(newItem.getData().getComments())
                    && newItem.getData().getComments().containsAll(oldItem.getData().getComments());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull CommentList oldItem, @NonNull CommentList newItem) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RedditUtilsNet.PAYLOAD_DIFF, newItem);
            return bundle;
        }
    };
    private INavigatorSource mNavigatorSource;

    public CommentsAdapter(INavigatorSource navigatorSource) {
        super(DIFF_CALLBACK);
        mNavigatorSource = navigatorSource;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mNavigatorSource = null;
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public void setNavigatorSource(INavigatorSource navigatorSource) {
        mNavigatorSource = navigatorSource;
    }

    @NonNull
    @Override
    public RedditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(RedditCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RedditViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setError() {


    }

    abstract class RedditViewHolder extends RecyclerView.ViewHolder {
        RedditViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(CommentList comment) {
        }

        void bind(NetworkCommentResult networkCommentResult) {
        }
    }


    class CommentViewHolder extends RedditViewHolder {

        private final RedditCommentBinding mBinding;
        private CommentData mCommentData;
        private View.OnClickListener mOnClickShareListener;

        CommentViewHolder(@NonNull RedditCommentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        void bind(CommentList comment) {
            if (comment.getData() != null && comment.getData().getComments() != null && comment.getData().getComments().get(0) != null) {
                mCommentData = comment.getData().getComments().get(0).getData();
                binding();
            }
        }

        private void binding() {
            if (mCommentData.getBodyHtml() != null)
                Markwon.setMarkdown(mBinding.body, mCommentData.getBodyHtml());
            mBinding.tvUsername.setText(mCommentData.getAuthor());
            RedditItemHelper.setTime(mBinding.createdTime, mCommentData.getCreatedUtc());
            mBinding.ivShare.setOnClickListener(RedditItemHelper.getShareListener(mNavigatorSource, mOnClickShareListener, mCommentData.getPermalink()));
        }


    }
}




