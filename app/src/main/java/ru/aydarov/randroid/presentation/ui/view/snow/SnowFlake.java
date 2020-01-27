package ru.aydarov.randroid.presentation.ui.view.snow;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * @author Aydarov Askhar 2020
 */
class SnowFlake {
    private static final double FLAKE_SIZE_LOWER = 7.0d;
    private static final double FLAKE_SIZE_UPPER = 20.0d;
    private static final double HALF_PI = 1.5707963267948966d;
    private static final double INCREMENT_LOWER = 2.0d;
    private static final double INCREMENT_UPPER = 3.0d;
    private static RandomSnow random = new RandomSnow();
    private double angle;
    private final double flakeSize;
    private final double increment;
    private final Paint paint;
    public final Point position;

    public static SnowFlake create(int width, int height, Paint paint2) {
        RandomSnow random2 = new RandomSnow();
        SnowFlake snowFlake = new SnowFlake(random2, new Point(random2.getRandom(width), random2.getRandom(height)), random2.getRandom(6.283185307179586d), random2.getRandom(INCREMENT_LOWER, INCREMENT_UPPER), random2.getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER), paint2);
        return snowFlake;
    }

    SnowFlake(RandomSnow random2, Point position2, double angle2, double increment2, double flakeSize2, Paint paint2) {
        random = random2;
        this.position = position2;
        this.angle = angle2;
        this.increment = increment2;
        this.flakeSize = flakeSize2;
        this.paint = paint2;
    }

    private void move(int width, int height) {
        double x = ((double) this.position.x) + (this.increment * Math.cos(this.angle));
        double y = ((double) this.position.y) + (this.increment * Math.sin(this.angle));
        double d = this.flakeSize;
        if (x < d + 1.0d || y < d + 1.0d) {
            this.position.set(((int) x) + 1, ((int) y) + 1);
        } else {
            this.position.set((int) x, (int) y);
        }
        if (!isInside(width, height)) {
            reset(width, height);
        }
    }

    private boolean isInside(int width, int height) {
        int x = this.position.x;
        int y = this.position.y;
        double d = (double) x;
        double d2 = this.flakeSize;
        return d > d2 && ((double) x) + d2 < ((double) width) && ((double) y) > d2 && ((double) y) + d2 < ((double) height);
    }

    private void reset(int width, int height) {
        if (((double) this.position.x) < this.flakeSize || ((double) this.position.x) + this.flakeSize > ((double) width)) {
            double d = this.angle;
            if (d < 0.0d) {
                this.angle = -3.141592653589793d - d;
            } else {
                this.angle = 3.141592653589793d - d;
            }
        } else if (((double) this.position.y) < this.flakeSize || ((double) this.position.y) + this.flakeSize > ((double) height)) {
            this.angle *= -1.0d;
        }
    }

    public void draw(Canvas canvas) {
        move(canvas.getWidth(), canvas.getHeight());
        canvas.drawCircle((float) this.position.x, (float) this.position.y, (float) this.flakeSize, this.paint);
    }
}
