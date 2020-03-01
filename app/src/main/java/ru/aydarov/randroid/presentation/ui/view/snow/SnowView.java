package ru.aydarov.randroid.presentation.ui.view.snow;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

import ru.aydarov.randroid.R;

/**
 * @author Aydarov Askhar 2020
 */
public class SnowView extends View {
    public static final int MAGIC_NUMBER = 150;
    public static final int MIN_SPEED = 11;
    private int mBlue;
    private int mGreen;
    private Paint mPaintFlakes = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mRed;
    private Paint mPaintLines = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[][] mAlphaForLines;
    private SnowFlake[] mSnowFlakes;
    private Runnable mMoveUpdateRunnable = SnowView.this::invalidate;
    private int mFlakesCount = 100;
    private int mOpacity = 255;
    private int mWidth;
    private int mHeight;
    private int mSpeed = 5;
    private int mFlakeSizeRatio;
    private int mSpeedRatio;


    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnowView);
            int red = typedArray.getInt(R.styleable.SnowView_red, getResources().getInteger(R.integer.red));
            int green = typedArray.getInt(R.styleable.SnowView_green, getResources().getInteger(R.integer.green));
            int blue = typedArray.getInt(R.styleable.SnowView_blue, getResources().getInteger(R.integer.blue));
            typedArray.recycle();
            setRGB(red, green, blue);
        }
    }

    {
        mSnowFlakes = new SnowFlake[mFlakesCount];
        mAlphaForLines = new int[mFlakesCount][mFlakesCount];
        int rgb = Color.rgb(mRed, mGreen, mBlue);
        updatePaints(rgb);
    }

    public void setRGB(int red, int green, int blue) {
        mRed = red;
        mGreen = green;
        mBlue = blue;
        int rgb = Color.rgb(red, green, blue);
        updatePaints(rgb);
    }

    private void updatePaints(int rgb) {
        mPaintFlakes.setStyle(Style.FILL);
        mPaintFlakes.setColor(rgb);
        mPaintLines.setColor(rgb);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            resize(w, h);
        }
    }

    public void resize(int width, int height) {
        mSnowFlakes = new SnowFlake[mFlakesCount];
        for (int i = 0; i < mFlakesCount; i++) {
            mWidth = width;
            mHeight = height;
            mSnowFlakes[i] = SnowFlake.create(mWidth, mHeight, mPaintFlakes);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateLinesOpacity();
        drawFlakes(canvas);
        drawLines(canvas);
        getHandler().postDelayed(mMoveUpdateRunnable, mSpeed);
    }

    private void drawFlakes(Canvas canvas) {
        for (SnowFlake snowFlake : mSnowFlakes) {
            snowFlake.draw(canvas);
        }
    }

    private void drawLines(Canvas canvas) {
        for (int i = 1; i < mFlakesCount; i++) {
            for (int j = i + 1; j < mFlakesCount; j++) {
                mPaintLines.setAlpha(mAlphaForLines[i][j]);
                canvas.drawLine((float) mSnowFlakes[i].getPosition().x, (float) mSnowFlakes[i].getPosition().y, (float) mSnowFlakes[j].getPosition().x, (float) mSnowFlakes[j].getPosition().y, mPaintLines);
            }
        }
    }

    public void setFlakesCount(int flakesCount) {
        mSnowFlakes = Arrays.copyOf(mSnowFlakes, flakesCount);
        for (int i = mFlakesCount; i < flakesCount; i++) {
            mSnowFlakes[i] = SnowFlake.create(mWidth, mHeight, mPaintFlakes);
            mSnowFlakes[i].setFlakeSize(mFlakeSizeRatio);
            mSnowFlakes[i].setIncrement(mSpeedRatio);
        }
        int[][] temp = new int[flakesCount][flakesCount];
        for (int i = 0; i < flakesCount; i++) {
            for (int j = i + 1; j < flakesCount; j++) {
                if (i < mFlakesCount && j < mFlakesCount)
                    temp[i][j] = mAlphaForLines[i][j];
            }
        }

        mAlphaForLines = temp;
        mFlakesCount = flakesCount;
    }

    public void setFlakeSize(int flakeSizeRatio) {
        mFlakeSizeRatio = flakeSizeRatio;
        for (SnowFlake snowFlake : mSnowFlakes) {
            snowFlake.setFlakeSize(mFlakeSizeRatio);
        }
    }

    public void setFlakeSpeed(int speedRatio) {
        mSpeedRatio = speedRatio;
        for (SnowFlake snowFlake : mSnowFlakes) {
            snowFlake.setIncrement(mSpeedRatio);
        }
    }

    private void calculateLinesOpacity() {
        for (int i = 0; i < mFlakesCount; i++) {
            for (int j = i + 1; j < mFlakesCount; j++) {
                double a = Math.hypot(mSnowFlakes[i].getPosition().x - mSnowFlakes[j].getPosition().x, mSnowFlakes[i].getPosition().y - mSnowFlakes[j].getPosition().y);
                if (a < MAGIC_NUMBER) {
                    mAlphaForLines[i][j] = mOpacity;
                } else {
                    mAlphaForLines[i][j] = 0;
                }
            }
        }
    }


}
