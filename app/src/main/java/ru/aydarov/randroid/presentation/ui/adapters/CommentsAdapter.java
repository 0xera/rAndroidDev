package ru.aydarov.randroid.presentation.ui.adapters;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import ru.aydarov.randroid.data.model.Comment;
import ru.aydarov.randroid.data.model.CommentList;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class CommentsAdapter extends ListAdapter<Comment, CommentsAdapter.CommentViewHolder> {

    private static final DiffUtil.ItemCallback<Comment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Comment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getData().getName().equals(newItem.getData().getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return !TextUtils.isEmpty(oldItem.getData().getBody()) && !TextUtils.isEmpty(newItem.getData().getBody()) && oldItem.getData().getBody().equals(newItem.getData().getBody())
                    && !TextUtils.isEmpty(oldItem.getData().getId()) && !TextUtils.isEmpty(newItem.getData().getId()) && oldItem.getData().getId().equals(newItem.getData().getId());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Comment oldItem, @NonNull Comment newItem) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RedditUtilsNet.PAYLOAD_DIFF, newItem);
            return bundle;
        }
    };

    protected CommentsAdapter(@NonNull DiffUtil.ItemCallback<CommentList> diffCallback) {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

    }

    abstract class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(RedditPost post) {
        }

        void bind(NetworkState networkState) {
        }
    }

}

