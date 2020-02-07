package ru.aydarov.randroid.presentation.ui.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.data.util.ImageUtil;
import ru.aydarov.randroid.presentation.ui.view.SwipeRefreshLayout;

/**
 * @author Aydarov Askhar 2020
 */
public class CustomBindingAdapter {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url) && !((Activity) imageView.getContext()).isDestroyed() && URLUtil.isValidUrl(url)) {
            Glide.with(imageView.getContext()).load(ImageUtil.getUri(url)).error((imageView.getId() == R.id.ivProfile) ? R.drawable.ic_add_profile : R.drawable.ic_add_image).into(imageView);
        }
    }


    @BindingAdapter({"refreshState", "refreshListener"})
    public static void refreshData(SwipeRefreshLayout refreshLayout, boolean isRefresh, SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setRefreshing(isRefresh);
        refreshLayout.setOnRefreshListener(listener);
        if (isRefresh)
            listener.onRefresh();
    }
}
