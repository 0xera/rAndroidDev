package ru.aydarov.randroid.presentation.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
/**
 * @author Aydarov Askhar 2020
 */
public class CircleImageViewRoundedBitmap extends AppCompatImageView {

    private AppCompatImageView mImageView;

    public CircleImageViewRoundedBitmap(Context context) {
        super(context);

    }

    public CircleImageViewRoundedBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    {

        initImageView();
    }
    @Override
    public void setImageResource(int resId) {
        initImageView();
        mImageView.setImageResource(resId);
        if (mImageView.getDrawable() != null)
            setImageDrawable(mImageView.getDrawable());
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        initImageView();
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            super.setImageDrawable(roundedBitmapDrawable);
        } else {
            super.setImageDrawable(null);
        }


    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        initImageView();
        if (bm != null) {
            mImageView.setImageBitmap(bm);
            setImageDrawable(mImageView.getDrawable());
        }
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        initImageView();
        if (uri != null) {
            mImageView.setImageURI(uri);
            setImageDrawable(mImageView.getDrawable());
        }
    }


    private void initImageView() {
        if(mImageView == null){
            mImageView = new AppCompatImageView(this.getContext());
        }
    }




}