package ru.aydarov.randroid.presentation.ui.bottom_sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.Nullable;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.AboutBottomSheetBinding;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class AboutAppBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String FRAGMENT_TAG = "tegos";
    private AboutBottomSheetBinding mAboutBottomSheetBinding;
    private Random mRnd = ThreadLocalRandom.current();

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
        mAboutBottomSheetBinding = AboutBottomSheetBinding.inflate(inflater, container, false);
        //     BottomSheetBehavior.from(mAboutBottomSheetBinding.getRoot()).setState(BottomSheetBehavior.STATE_EXPANDED);
        mAboutBottomSheetBinding.tvApp.setOnClickListener(v->
                mAboutBottomSheetBinding.snowView.setRGB(mRnd.nextInt(200), mRnd.nextInt(200), mRnd.nextInt(200))
        );
        return mAboutBottomSheetBinding.getRoot();
    }

    public static String getFragmentTag() {
        return FRAGMENT_TAG;
    }

}
