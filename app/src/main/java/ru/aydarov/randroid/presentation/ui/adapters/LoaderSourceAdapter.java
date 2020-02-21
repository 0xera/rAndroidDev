package ru.aydarov.randroid.presentation.ui.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.domain.util.RedditUtils;

public class LoaderSourceAdapter {
    private static final RequestOptions sRequestOptions = new RequestOptions().priority(Priority.IMMEDIATE);


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url) && !((Activity) imageView.getContext()).isDestroyed() && URLUtil.isValidUrl(url)) {
            Glide.with(imageView.getContext()).load(url).apply(sRequestOptions).error(getResourceIdHasError(imageView)).into(imageView);
        }
    }

    public static void loadImagePost(ImageView imageView, ProgressBar progressBar, String url) {
        if (!TextUtils.isEmpty(url) && !((Activity) imageView.getContext()).isDestroyed() && URLUtil.isValidUrl(url)) {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            Glide.with(imageView.getContext()).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    return false;
                }
            }).apply(sRequestOptions).error(getResourceIdHasError(imageView)).into(imageView);
        }
    }

    @BindingAdapter("bannerImageUrl")
    public static void loadImageBanner(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url) && !((Activity) imageView.getContext()).isDestroyed() && URLUtil.isValidUrl(url)) {
            Glide.with(imageView.getContext()).load(RedditUtils.getUri(url)).apply(sRequestOptions).error(getResourceIdHasError(imageView)).into(imageView);
        }
    }

    private static int getResourceIdHasError(ImageView imageView) {
        if (imageView.getId() == R.id.ivProfile) return R.drawable.ic_add_profile;
        else if (imageView.getId() == R.id.ivBackground) return R.drawable.ic_add_image;
        else return R.drawable.ic_error_outline;
    }

    @BindingAdapter("videoUrl")
    public static void loadVideo(PlayerView playerView, String url) {

        if (!TextUtils.isEmpty(url) && !((Activity) playerView.getContext()).isDestroyed() && URLUtil.isValidUrl(url)) {
//                    videoView.setVideoPath(url);
//                    MediaController controller = new MediaController(videoView.getContext());
//                    controller.setAnchorView(videoView);
//                    videoView.setMediaController(controller);
//                    videoView.requestFocus(0);
//                    videoView.setOnPreparedListener(mp -> {
//                        videoView.start();
//                    });

            SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(playerView.getContext());
            playerView.setPlayer(simpleExoPlayer);
            String userAgent = Util.getUserAgent(playerView.getContext(), playerView.getContext().getString(R.string.app_name));
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(playerView.getContext(), userAgent);
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(RedditUtils.getUri(url)));
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

        }
    }
}


//    @BindingAdapter({"refreshState", "refreshListener"})
//    public static void refreshData(SwipeRefreshLayout refreshLayout, boolean isRefresh, SwipeRefreshLayout.OnRefreshListener listener) {
//        refreshLayout.setRefreshing(isRefresh);
//        refreshLayout.setOnRefreshListener(listener);
//        if (isRefresh)
//            listener.onRefresh();
//    }

