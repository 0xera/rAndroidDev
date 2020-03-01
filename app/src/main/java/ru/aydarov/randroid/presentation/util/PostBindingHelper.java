package ru.aydarov.randroid.presentation.util;

import android.text.TextUtils;
import android.view.View;

import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.databinding.RedditPostItemBinding;
import ru.aydarov.randroid.presentation.common.INavigatorSource;
import ru.aydarov.randroid.presentation.ui.adapters.LoaderSourceAdapter;

public class PostBindingHelper {

    public static void binding(INavigatorSource navigatorSource, RedditPostItemBinding binding,
                               RedditPost post, View.OnClickListener onClickMediaListener,
                               View.OnClickListener onClickShareListener) {
        if (post != null) {
            binding.ivImage.layout(0, 0, 0, 0);
            String urlPreview = RedditUtils.getUrlPreview(post);
            if (!TextUtils.isEmpty(urlPreview)) {
                binding.ivImage.setVisibility(View.VISIBLE);
                binding.ivImage.setOnClickListener(RedditItemHelper.getMediaOpenListener(navigatorSource, onClickMediaListener, post));
                LoaderSourceAdapter.loadImagePost(binding.ivImage, binding.pbProgress, urlPreview);
            } else {
                binding.ivImage.setVisibility(View.GONE);
            }
            binding.ivShare.setOnClickListener(RedditItemHelper.getShareListener(navigatorSource, onClickShareListener, post.getPermalink()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvUsername.setText(post.getAuthor());
            RedditItemHelper.setTime(binding.createdTime, post.getCreated());
            RedditItemHelper.setPostType(binding.tvType, post);
            RedditItemHelper.highlightText(binding.tvTitle, post.getTitle(), post.getSearchQuery());
            RedditItemHelper.setSelfText(binding.selfText, post);
        }
    }
}
