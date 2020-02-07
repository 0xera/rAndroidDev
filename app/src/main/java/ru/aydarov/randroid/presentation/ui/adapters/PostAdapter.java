package ru.aydarov.randroid.presentation.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public class PostAdapter extends PagedListAdapter {

    private final DiffUtil.ItemCallback<RedditPost> DIFF_CALLBACK = new DiffUtil.ItemCallback<RedditPost>() {
        @Override
        public boolean areItemsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return false;
        }
    };

    protected PostAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
