package ru.aydarov.randroid.presentation.ui.view.snow;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * @author Aydarov Askhar 2020
 */
class SnowFlake {
    private static final double FLAKE_SIZE_LOWER = 7.0;
    private static final double FLAKE_SIZE_UPPER = 20.0;
    private static final double HALF_PI = Math.PI / 2;
    private static final double TWO_PI = Math.PI / 2;
    private static final double INCREMENT_LOWER = 1.0;
    private static final double INCREMENT_UPPER = 4.0;
    private static final int MAGIC_NUMBER = 1;
    public static final int RATIO = 100;
    private final double REAL_SIZE;
    private final double REAL_INCREMENT;

    private final Point mPosition;

    private double mAngle;
    private double mIncrement;
    private double mFlakeSize;
    private final Paint mPaint;
    private static RandomSnow sRandomSnow = new RandomSnow();

    public static SnowFlake create(int width, int height, Paint paint2) {
        return new SnowFlake(new Point(sRandomSnow.getRandom(width), sRandomSnow.getRandom(height)), sRandomSnow.getRandom(TWO_PI), sRandomSnow.getRandom(INCREMENT_LOWER, INCREMENT_UPPER), sRandomSnow.getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER), paint2);
    }

    private SnowFlake(Point position, double angle, double increment, double flakeSize, Paint paint) {
        mPosition = position;
        mAngle = angle;
        mIncrement = increment;
        REAL_INCREMENT = increment;
        mFlakeSize = flakeSize;
        REAL_SIZE = flakeSize;
        mPaint = paint;
    }

    private void move(int width, int height) {
        double x = mPosition.x + mIncrement * Math.cos(mAngle);
        double y = mPosition.y + mIncrement * Math.sin(mAngle);
        double d = mFlakeSize;
        if (x <= d || y <= d) {
            mPosition.set((int) (x + MAGIC_NUMBER), (int) (y + MAGIC_NUMBER));
        } else {
            mPosition.set((int) x, (int) y);
        }
        if (!isInside(width, height)) {
            updateAngle(width, height);
        }
    }

    private boolean isInside(int width, int height) {
        return mPosition.x > mFlakeSize &&
                mPosition.x + mFlakeSize < width &&
                mPosition.y > mFlakeSize &&
                mPosition.y + mFlakeSize < height;
    }

    private void updateAngle(int width, int height) {
        if (mPosition.x < mFlakeSize || mPosition.x + mFlakeSize > width) {
            double d = mAngle;
            if (d < 0.0) {
                mAngle = Math.PI * -1 - d;
            } else {
                mAngle = Math.PI - d;
            }
        } else if (mPosition.y < mFlakeSize || mPosition.y + mFlakeSize > height) {
            mAngle *= -1.0;
        }
    }

    public void draw(Canvas canvas) {
        move(canvas.getWidth(), canvas.getHeight());
        canvas.drawCircle(mPosition.x, mPosition.y, (float) mFlakeSize, mPaint);
    }

    public Point getPosition() {
        return mPosition;
    }

    public void setFlakeSize(int flakeSizeRatio) {
        mFlakeSize = REAL_SIZE * flakeSizeRatio  / RATIO;
    }

    public void setIncrement(int speedRatio) {
        mIncrement = REAL_INCREMENT * speedRatio / RATIO;
    }


}
