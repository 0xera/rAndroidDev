package ru.aydarov.randroid.presentation.util;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public class RedditUtils {
    @NotNull
    public static String getUri(String url) {
        String[] split = url.split("\\?");

        return split[0];
    }


    public static boolean isLinkOrRichVideo(RedditPost post) {
        return (isRichVideo(post) || isLink(post));
    }

    public static boolean isRichVideo(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getUrl()) && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals("rich:video");
    }

    public static boolean isLink(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getUrl()) && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals("link");
    }

    public static boolean isSelfText(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals("self");
    }

    public static boolean isImage(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals("image");
    }

    public static boolean isVideo(RedditPost post) {
        return post != null && post.getMedia() != null && post.getMedia().getVideo() != null;
    }

    public static String getUrlPreview(RedditPost post) {
        if (post != null && post.getPreview() != null && post.getPreview().getImages() != null) {
            List<RedditPost.Preview.Image.Resolution> images = post.getPreview().getImages().get(0).getResolutions();
            if (images != null) {
                int index;
                if (images.size() > 0) {
                    index = 0;
                    for (int i = 0; i < images.size(); i++) {
                        if (images.get(i).getHeight() > images.get(index).getHeight())
                            index = i;
                    }
                    RedditPost.Preview.Image.Resolution source = images.get(index);
                    if (source != null && !TextUtils.isEmpty(source.getUrl())) {
                        return source.getUrl();
                    }
                }

            } else {
                RedditPost.Preview.Image.Source source = post.getPreview().getImages().get(0).getSource();
                if (source != null && !TextUtils.isEmpty(source.getUrl()))
                    return source.getUrl();
            }

        }
        return null;
    }

}

