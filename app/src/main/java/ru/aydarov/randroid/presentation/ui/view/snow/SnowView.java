package ru.aydarov.randroid.presentation.ui.view.snow;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Array;

import ru.aydarov.randroid.R;

/**
 * @author Aydarov Askhar 2020
 */
public class SnowView extends View {
    private static final int DELAY = 5;
    private static final int NUM_SNOWFLAKES = 40;
    private int blue = 41;
    private int green = 43;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int red = 44;
    private Runnable runnable = SnowView.this::invalidate;
    private SnowFlake[] snowflakes;
    private Paint mPaintLine;

    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.paint.setColor(Color.argb(255, red, green, blue));
    }

    public SnowView(Context context) {
        super(context);
    }

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

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {

        mPaintLine = new Paint();
    }

    /* access modifiers changed from: protected */
    public void resize(int width, int height) {
        this.paint.setColor(Color.argb(255, this.red, this.green, this.blue));
        this.paint.setStyle(Style.FILL_AND_STROKE);
        this.snowflakes = new SnowFlake[100];
        for (int i = 0; i < 100; i++) {
            this.snowflakes[i] = SnowFlake.create(width, height, this.paint);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!(w == oldw && h == oldh) && w > 0 && h > 0) {
            resize(w, h);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int[] dimensions = {100, 100};
        Double[][] modul = (Double[][]) Array.newInstance(Double.class, dimensions);
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                Double a = Math.sqrt((double) (((this.snowflakes[i].position.x - this.snowflakes[j].position.x) * (this.snowflakes[i].position.x - this.snowflakes[j].position.x)) + ((this.snowflakes[i].position.y - this.snowflakes[j].position.y) * (this.snowflakes[i].position.y - this.snowflakes[j].position.y))));
                if (a < 255.0d) {
                    modul[i][j] = a;
                    if (a < 150.0d) {
                        Double[] dArr = modul[i];
                        dArr[j] = dArr[j] + 100.0d;
                    }
                } else {
                    modul[i][j] = 255.0d;
                }
            }
        }
        for (SnowFlake snowFlake : this.snowflakes) {
            snowFlake.draw(canvas);
        }

        mPaintLine.setColor(Color.rgb(this.red, this.green, this.blue));
        for (int i2 = 1; i2 < 100; i2++) {
            for (int j2 = i2 + 1; j2 < 100; j2++) {
                mPaintLine.setAlpha(255 - modul[i2][j2].intValue());
                canvas.drawLine((float) this.snowflakes[i2].position.x, (float) this.snowflakes[i2].position.y, (float) this.snowflakes[j2].position.x, (float) this.snowflakes[j2].position.y, mPaintLine);
            }
        }
        getHandler().postDelayed(this.runnable, 5);
    }
}
