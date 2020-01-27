package ru.aydarov.randroid.presentation.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import ru.aydarov.randroid.R;

/**
 * @author Aydarov Askhar 2020
 */
public class CircleImageView extends AppCompatImageView {

    private AppCompatImageView mImageView;

    public CircleImageView(Context context) {
        super(context);
        mImageView = new AppCompatImageView(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            Drawable drawable = typedArray.getDrawable(R.styleable.CircleImageView_srcCircle);
            typedArray.recycle();
            setImage(drawable);
        }
    }


    public void setImage(Bitmap bm) {
        if (bm != null) {
            mImageView.setImageBitmap(bm);
            setImage(mImageView.getDrawable());
        }
    }

    public void setImage(int resId) {
        mImageView.setImageResource(resId);
        if (mImageView.getDrawable() != null)
            setImage(mImageView.getDrawable());

    }

    public void setImage(@Nullable Uri uri) {
        if (uri != null) {
            mImageView.setImageURI(uri);
            setImage(mImageView.getDrawable());
        }
    }

    public void setImage(Drawable drawable) {
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            setImageDrawable(roundedBitmapDrawable);
        }
    }


}