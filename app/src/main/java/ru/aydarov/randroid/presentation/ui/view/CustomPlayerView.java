package ru.aydarov.randroid.presentation.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.exoplayer2.ui.PlayerView;

public class CustomPlayerView extends PlayerView {
    public CustomPlayerView(Context context) {
        super(context);
    }

    public CustomPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean hideController;

    {
        hideController = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (hideController) {
            hideController();
            hideController = false;
        } else {
            showController();
            hideController = true;
        }
        super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        super.onTrackballEvent(ev);
        return false;
    }
}
