package ru.aydarov.randroid.presentation.ui.bottom_sheet;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.FragmentAboutBottomSheetBinding;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class AboutAppBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String FRAGMENT_TAG = "tegos";
    private static final int BOUND = 150;
    private static final int BOUND_DEVIATION = 50;
    public static final int MAX_COUNT = 100;
    public static final int MAX_SIZE = 200;
    public static final int MAX_SPEED = 500;
    private static final String SPEED_KEY = "speed";
    private static final String SIZE_KEY = "size";
    private static final String COUNT_KEY = "count";
    private FragmentAboutBottomSheetBinding mAboutBottomSheetBinding;
    private Random mRnd = ThreadLocalRandom.current();
    private SeekBar.OnSeekBarChangeListener mSeekBarSpeedListener;
    private SeekBar.OnSeekBarChangeListener nSeekBarSizeListener;
    private SeekBar.OnSeekBarChangeListener mSeekBarCountListener;
    private int mSpeed = 100;
    private int mSize = 100;
    private int mCount = 50;
    private Runnable mAction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ThemeHelper.getTheme(requireActivity())) {
            setStyle(STYLE_NORMAL, R.style.BottomSheetDialogLight);

        } else {
            setStyle(STYLE_NORMAL, R.style.BottomSheetDialogDark);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mAboutBottomSheetBinding = FragmentAboutBottomSheetBinding.inflate(inflater, container, false);
        mAboutBottomSheetBinding.tvApp.setOnClickListener(v ->
                changeColor()
        );
        return mAboutBottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configSeekBars();
        changeColor();
        if (savedInstanceState != null) {
            mSpeed = savedInstanceState.getInt(SPEED_KEY);
            mSize = savedInstanceState.getInt(SIZE_KEY);
            mCount = savedInstanceState.getInt(COUNT_KEY);
        }
        mAboutBottomSheetBinding.snowView.post(mAction);
        super.onViewCreated(view, savedInstanceState);
    }

    private void configSeekBars() {
        mAboutBottomSheetBinding.flakesSpeed.setMax(MAX_SPEED);
        mAboutBottomSheetBinding.flakesSize.setMax(MAX_SIZE);
        mAboutBottomSheetBinding.flakesCount.setMax(MAX_COUNT);
        setSeekBarsListeners();
    }

    private void setSeekBarsListeners() {
        mSeekBarSpeedListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAboutBottomSheetBinding.snowView.setFlakeSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        nSeekBarSizeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAboutBottomSheetBinding.snowView.setFlakeSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        mSeekBarCountListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAboutBottomSheetBinding.snowView.setFlakesCount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        mAction = () -> {
            mAboutBottomSheetBinding.flakesSpeed.setProgress(mSpeed);
            mAboutBottomSheetBinding.flakesSize.setProgress(mSize);
            mAboutBottomSheetBinding.flakesCount.setProgress(mCount);
            mAboutBottomSheetBinding.snowView.setFlakeSpeed(mSpeed);
            mAboutBottomSheetBinding.snowView.setFlakeSize(mSize);
            mAboutBottomSheetBinding.snowView.setFlakesCount(mCount);
        };
    }

    @Override
    public void onStart() {
        mAboutBottomSheetBinding.flakesSpeed.setOnSeekBarChangeListener(mSeekBarSpeedListener);
        mAboutBottomSheetBinding.flakesSize.setOnSeekBarChangeListener(nSeekBarSizeListener);
        mAboutBottomSheetBinding.flakesCount.setOnSeekBarChangeListener(mSeekBarCountListener);
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SPEED_KEY, mAboutBottomSheetBinding.flakesSpeed.getProgress());
        outState.putInt(SIZE_KEY, mAboutBottomSheetBinding.flakesSize.getProgress());
        outState.putInt(COUNT_KEY, mAboutBottomSheetBinding.flakesCount.getProgress());
        super.onSaveInstanceState(outState);
    }

    private void changeColor() {
        int red = mRnd.nextInt(BOUND) + BOUND_DEVIATION;
        int green = mRnd.nextInt(BOUND) + BOUND_DEVIATION;
        int blue = mRnd.nextInt(BOUND) + BOUND_DEVIATION;
        int rgb = Color.rgb(red, green, blue);
        mAboutBottomSheetBinding.snowView.setRGB(red, green, blue);
        changeSeekBarColor(mAboutBottomSheetBinding.flakesSpeed, rgb);
        changeSeekBarColor(mAboutBottomSheetBinding.flakesSize, rgb);
        changeSeekBarColor(mAboutBottomSheetBinding.flakesCount, rgb);
    }

    private void changeSeekBarColor(SeekBar seekBar, int color) {
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public static String getFragmentTag() {
        return FRAGMENT_TAG;
    }

}
