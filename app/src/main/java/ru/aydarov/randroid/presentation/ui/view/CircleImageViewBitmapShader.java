package ru.aydarov.randroid.presentation.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author Aydarov Askhar 2020
 */
public class CircleImageViewBitmapShader extends AppCompatImageView {

    private RectF mDrawableRect;
    private Paint mBitmapPaint;
    private Bitmap mBitmap;
    private float mDrawableRadius;
    private Matrix mShaderMatrix;
    private BitmapShader mBitmapShader;

    public CircleImageViewBitmapShader(Context context) {
        super(context);
    }

    public CircleImageViewBitmapShader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageViewBitmapShader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        shadeBitmap();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        shadeBitmap();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        shadeBitmap();
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        newBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        newBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        newBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        newBitmap();
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void newBitmap() {
        if (getDrawable() != null) {
            mBitmap = getBitmapFromDrawable(getDrawable());
            shadeBitmap();
        }
    }

    private void shadeBitmap() {

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        if (mBitmapPaint == null)
            mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);


        mBitmap.getHeight();
        mBitmap.getWidth();
        mDrawableRect = calculateBounds();

        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);
        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;
        if (mShaderMatrix == null)
            mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);

        if (mBitmap.getWidth() * mDrawableRect.height() > mDrawableRect.width() * mBitmap.getHeight()) {
            scale = mDrawableRect.height() / (float) mBitmap.getHeight();
        } else {
            scale = mDrawableRect.width() / (float) getWidth();
        }
        float dx = (mDrawableRect.width() - mBitmap.getWidth() * scale) * 0.5f;
        float dy = (mDrawableRect.height() - mBitmap.getHeight() * scale) * 0.5f;

        mShaderMatrix.setScale(scale, scale, mDrawableRect.centerX(), mDrawableRect.centerY());
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint);
    }

    @Override
    public ScaleType getScaleType() {
        return ScaleType.CENTER_INSIDE;
    }

    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }


}

