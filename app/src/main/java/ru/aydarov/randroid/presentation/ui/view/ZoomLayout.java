package ru.aydarov.randroid.presentation.ui.view;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Aydarov Askhar 2020
 */
public class ZoomLayout extends FrameLayout {

    private float mScaleFactor = 1f;

    private float mPositionX;
    private float mPositionY;
    private float mLastTouchX;
    private float mLastTouchY;
    private static final int SWIPE_MIN = 400;
    private static final int SWIPE_MAX = 700;
    private static final int SWIPE_VELOCITY = 400;
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerID = INVALID_POINTER_ID;
    private final static float MIN_ZOOM = 1.0f;
    private final static float MAX_ZOOM = 1.5f;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mDoubleTapGesture;
    private GestureDetector mSwipeGesture;
    private boolean mIsActionBarHidden = false;

    private class ZoomScaleGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener {


        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));

            invalidate();
            return true;
        }

    }

    private class DoubleTapGesture extends GestureDetector.SimpleOnGestureListener {
        private static final long DEFAULT_ANIMATION_DURATION = 100;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            hideAndShowMenu();
            return true;
        }


        @Override
        public boolean onDoubleTap(MotionEvent event) {
            ValueAnimator valueAnimator;
            if (mScaleFactor > 1.f) {
                PropertyValuesHolder posX = PropertyValuesHolder.ofFloat("posX", mPositionX, 0f);
                PropertyValuesHolder posY = PropertyValuesHolder.ofFloat("posY", mPositionY, 0f);
                PropertyValuesHolder scale = PropertyValuesHolder.ofFloat("scale", mScaleFactor, 1.f);
                valueAnimator = ValueAnimator.ofPropertyValuesHolder(scale, posX, posY);
            } else {
                PropertyValuesHolder scale = PropertyValuesHolder.ofFloat("scale", mScaleFactor, mScaleFactor * 1.3f);
                valueAnimator = ValueAnimator.ofPropertyValuesHolder(scale);
            }
            valueAnimator.addUpdateListener(animation -> {
                mScaleFactor = Math.max(MIN_ZOOM, Math.min((float) animation.getAnimatedValue("scale"), MAX_ZOOM));
                if (animation.getAnimatedValue("posX") != null && animation.getAnimatedValue("posX") != null) {
                    mPositionX = (float) animation.getAnimatedValue("posX");
                    mPositionY = (float) animation.getAnimatedValue("posY");
                }
                invalidate();
            });

            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(DEFAULT_ANIMATION_DURATION);
            valueAnimator.start();
            invalidate();
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    private void hideAndShowMenu() {

        if (mIsActionBarHidden) {
            show();
            postDelayed(this::hide, 2000);
        } else {
            hide();
            mIsActionBarHidden = true;
        }
    }

    private void hide() {
        Window window = ((Activity) getContext()).getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void show() {
        Window window = ((Activity) getContext()).getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mIsActionBarHidden = false;
    }

    private class SwipeGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if ((Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX) || (Math.abs(e2.getX() - e1.getX()) > SWIPE_MIN && Math.abs(velocityX) > SWIPE_VELOCITY)) {
                ((AppCompatActivity) getContext()).supportFinishAfterTransition();
                return true;
            }


            return false;
        }
    }


    public ZoomLayout(Context context) {
        super(context);
    }


    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ZoomScaleGesture());
        mScaleGestureDetector.setQuickScaleEnabled(true);
        mDoubleTapGesture = new GestureDetector(getContext(), new DoubleTapGesture());
        mSwipeGesture = new GestureDetector(getContext(), new SwipeGesture());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                hideAndShowMenu();
            }
        }, 2000);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (getChildAt(0) != null) {
            canvas.translate(mPositionX, mPositionY);
            canvas.scale(mScaleFactor, mScaleFactor);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mSwipeGesture.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        mDoubleTapGesture.onTouchEvent(event);

        final int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {

                final float x = event.getX();
                final float y = event.getY();

                mLastTouchX = x;
                mLastTouchY = y;

                mActivePointerID = event.getPointerId(0);

                break;
            }
            case MotionEvent.ACTION_MOVE: {

                final int pointerIndex = event.findPointerIndex(mActivePointerID);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);

                if (!mScaleGestureDetector.isInProgress()) {

                    final float distanceX = x - mLastTouchX;
                    final float distanceY = y - mLastTouchY;

                    mPositionX += distanceX;
                    mPositionY += distanceY;

                    invalidate();

                }
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerID = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerID) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerID = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

}

