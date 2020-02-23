package ru.aydarov.randroid.presentation.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.core.content.ContextCompat;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.data.model.Media;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.presentation.activty.ImageViewActivity;
import ru.aydarov.randroid.presentation.activty.VideoViewActivity;
import ru.aydarov.randroid.presentation.common.INavigatorSource;
import ru.noties.markwon.Markwon;

import static android.util.Patterns.WEB_URL;
import static ru.aydarov.randroid.data.util.Constants.SRC_OPEN_KEY;
import static ru.aydarov.randroid.data.util.RedditUtilsNet.IMAGE;
import static ru.aydarov.randroid.data.util.RedditUtilsNet.LINK;
import static ru.aydarov.randroid.data.util.RedditUtilsNet.TEXT;
import static ru.aydarov.randroid.data.util.RedditUtilsNet.VIDEO;

public class RedditItemHelper {

    private static final String PATTERN_DATE = "MMM d, yyyy, HH:mm";

    static void setPostType(TextView textView, RedditPost post) {
        if (RedditUtils.isVideo(post) || RedditUtils.isRichVideo(post)) {
            textView.setText(VIDEO);
        } else if (!TextUtils.isEmpty(RedditUtils.getUrlPreview(post)) && RedditUtils.isImage(post)) {
            textView.setText(IMAGE);
        } else if (RedditUtils.isSelfText(post) || !TextUtils.isEmpty(post.getSelfText())) {
            textView.setText(TEXT);
        } else if (RedditUtils.isLink(post) || (post.getPostHint() == null && !TextUtils.isEmpty(post.getUrl()))) {
            textView.setText(LINK);
        }
    }

    static void setSelfText(TextView selfText, RedditPost post) {
        String text = null;
        if (!TextUtils.isEmpty(post.getSelfText())) {
            text = post.getSelfText().trim();
        } else if (RedditUtils.isLink(post) || post.getPostHint() == null) {
            text = post.getUrl();
        }

        if (!TextUtils.isEmpty(text)) {
            try {
                Markwon.setMarkdown(selfText, text);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Linkify.addLinks(selfText, WEB_URL, "");
        }

    }

    static void highlightText(TextView selfText, String text, String searchQuery) {
        if (!TextUtils.isEmpty(searchQuery) && !TextUtils.isEmpty(text)) {
            String[] splitQuery = searchQuery.toLowerCase().split(" ");
            SpannableString spannableString = new SpannableString(text);
            String textForSearching = text.toLowerCase();
            for (String query : splitQuery) {
                int indexOfEnd = textForSearching.indexOf(query);
                for (int index = 0; index < textForSearching.length() && indexOfEnd != -1; index = indexOfEnd + 1) {

                    indexOfEnd = textForSearching.indexOf(query, index);
                    if (indexOfEnd == -1)
                        break;
                    else {
                        spannableString.setSpan(new BackgroundColorSpan(ContextCompat.getColor(selfText.getContext(), R.color.highlight_found_text)), indexOfEnd, indexOfEnd + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selfText.setText(spannableString, TextView.BufferType.SPANNABLE);
                    }
                }
            }
        }
    }

    static void setTime(TextView textView, String value) {
        if (!TextUtils.isEmpty(value) && !((Activity) textView.getContext()).isDestroyed()) {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                locale = textView.getResources().getConfiguration().getLocales().get(0);
            else locale = textView.getResources().getConfiguration().locale;
            Calendar postTimeCalendar = Calendar.getInstance();
            long millis = (long) Double.parseDouble(value) * 1000;
            postTimeCalendar.setTimeInMillis(millis);
            String formattedPostTime = new SimpleDateFormat(PATTERN_DATE,
                    locale).format(postTimeCalendar.getTime());
            textView.setText(formattedPostTime);
        }
    }


    private static Intent getMediaIntent(RedditPost post, View view) {
        Intent intent = null;
        if (RedditUtils.isVideo(post)) {
            Media.Video video = Objects.requireNonNull(post.getMedia()).getVideo();
            String url = Objects.requireNonNull(video).getFallback();
            if (!TextUtils.isEmpty(url))
                intent = new Intent(view.getContext(), VideoViewActivity.class).putExtra(SRC_OPEN_KEY, url);
        } else {
            String url = RedditUtils.getUrlPreview(post);
            if (!TextUtils.isEmpty(url))
                intent = new Intent(view.getContext(), ImageViewActivity.class).putExtra(SRC_OPEN_KEY, url);
        }
        if (RedditUtils.isLinkOrRichVideo(post)) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));

        }
        return intent;
    }

    @NotNull
    static View.OnClickListener getShareListener(INavigatorSource navigatorSource,
                                                 View.OnClickListener onClickListener, String permalink) {
        if (onClickListener == null) {
            onClickListener = v -> shareLink(navigatorSource, RedditUtilsNet.API_BASE_URI + permalink);
        }
        return onClickListener;
    }

    private static void shareLink(INavigatorSource navigatorSource, String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        navigatorSource.onShare(intent);
    }

    @NotNull
    static View.OnClickListener getMediaOpenListener(INavigatorSource navigatorSource,
                                                     View.OnClickListener onClickMediaListener, RedditPost post) {
        if (onClickMediaListener == null) {
            onClickMediaListener = v -> {
                if (post != null) {
                    Intent intent = RedditItemHelper.getMediaIntent(post, v);
                    if (intent != null) {
                        if (!TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals(Intent.ACTION_VIEW)) {
                            v.getContext().startActivity(intent);
                        } else {
                            navigatorSource.navigateToSourceViewActivity(v, intent);
                        }
                    }
                }
            };
        }
        return onClickMediaListener;
    }


    static View.OnClickListener getCommentsOpenListener(INavigatorSource navigatorSource,
                                                        View.OnClickListener onClickListener, RedditPost post) { {
            if (onClickListener == null) {
                onClickListener = v -> navigatorSource.openComments(post);
            }
            return onClickListener;
        }

    }
}
