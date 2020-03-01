package ru.aydarov.randroid.presentation.util;

import android.text.TextUtils;

import java.util.List;

import ru.aydarov.randroid.data.model.Preview;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public class RedditUtils {

    public static final String IMAGE = "image";
    public static final String SELF = "self";
    public static final String RICH_VIDEO = "rich:video";
    public static final String LINK = "link";
    public static final String AUDIO = "audio";

    public static String getUrl(String url) {
        String[] split = url.split("\\?");
        return split[0];
    }


    public static boolean isLinkOrRichVideo(RedditPost post) {
        return (isRichVideo(post) || isLink(post));
    }

    public static boolean isRichVideo(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getUrl()) && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals(RICH_VIDEO);
    }

    public static boolean isLink(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getUrl()) && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals(LINK);
    }

    public static boolean isSelfText(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals(SELF);
    }

    public static boolean isImage(RedditPost post) {
        return post != null && !TextUtils.isEmpty(post.getPostHint()) && post.getPostHint().equals(IMAGE);
    }

    public static boolean isVideo(RedditPost post) {
        return post != null && post.getMedia() != null && post.getMedia().getVideo() != null;
    }

    public static String getUrlPreview(RedditPost post) {
        if (post != null && post.getPreview() != null && post.getPreview().getImages() != null) {
            List<Preview.Image.Resolution> images = post.getPreview().getImages().get(0).getResolutions();
            if (images != null) {
                return searchImage(images);

            } else {
                Preview.Image.Source source = post.getPreview().getImages().get(0).getSource();
                if (source != null && !TextUtils.isEmpty(source.getUrl()))
                    return source.getUrl();
            }
        }
        return null;
    }

    private static String searchImage(List<Preview.Image.Resolution> images) {
        int index;
        if (images.size() > 0) {
            index = 0;
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).getHeight() > images.get(index).getHeight())
                    index = i;
            }
            Preview.Image.Resolution source = images.get(index);
            if (source != null && !TextUtils.isEmpty(source.getUrl())) {
                return source.getUrl();
            }
        }
        return null;
    }

    public static String getAudioUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            StringBuilder stringBuilder = new StringBuilder(getUrl(url));
            int lastIndexOf = stringBuilder.lastIndexOf("/");
            stringBuilder.delete(lastIndexOf + 1, url.length());
            stringBuilder.append(AUDIO);
            return stringBuilder.toString();
        }
        return null;
    }
}

