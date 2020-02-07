package ru.aydarov.randroid.presentation.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.aydarov.randroid.R;

/**
 * @author Aydarov Askhar 2020
 */
public class SwipeRefreshLayout extends androidx.swiperefreshlayout.widget.SwipeRefreshLayout {

    private int mSchemeColor;
    private int mSchemeBackgroundColor;

    public SwipeRefreshLayout(@NonNull Context context) {
        super(context, null);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshLayout);
            mSchemeColor = typedArray.getColor(R.styleable.SwipeRefreshLayout_scheme_color, getResources().getColor(R.color.light_accent));
            mSchemeBackgroundColor = typedArray.getColor(R.styleable.SwipeRefreshLayout_scheme_color_background, getResources().getColor(R.color.lightWinBackground));
            typedArray.recycle();
        }
        setColors();

    }

    private void setColors() {
        this.setColorSchemeColors(mSchemeColor);
        this.setProgressBackgroundColorSchemeColor(mSchemeBackgroundColor);
    }
}
