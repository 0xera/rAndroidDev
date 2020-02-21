package ru.aydarov.randroid.presentation.ui.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.SortBottomSheetBinding;
import ru.aydarov.randroid.domain.util.SortTypeHelper;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class SortBottomSheetFragment extends BottomSheetDialogFragment {

    private static final int HOT_SORT = 0;
    private static final int TOP_SORT = 1;
    private static final int NEW_SORT = 2;
    private static final int CONTROVERSIAL_SORT = 3;


    private static final String FRAGMENT_TAG = "Bottom_sheet_tag";
    private SortListener mListener;
    private SortBottomSheetBinding mSortBottomSheetBinding;
    private RadioGroup mRadioGroup;

    public static SortBottomSheetFragment newInstance() {
        return new SortBottomSheetFragment();
    }

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
        mSortBottomSheetBinding = SortBottomSheetBinding.inflate(inflater, container, false);
        mSortBottomSheetBinding.setLifecycleOwner(this);
        initView();
        return mSortBottomSheetBinding.getRoot();
    }

    private void initView() {
        mRadioGroup = mSortBottomSheetBinding.radioGroupSort;
        setCheckedButton(SortTypeHelper.getSortType(requireActivity()));
        mRadioGroup.setOnCheckedChangeListener(getOnCheckedChangeListener());
    }

    private void setCheckedButton(int sortType) {
        switch (sortType) {
            case 0:
                mSortBottomSheetBinding.radioBtnHot.setChecked(true);
                break;
            case 1:
                mSortBottomSheetBinding.radioBtnTop.setChecked(true);
                break;
            case 2:
                mSortBottomSheetBinding.radioBtnNew.setChecked(true);
                break;
            case 3:
                mSortBottomSheetBinding.radioBtnControversial.setChecked(true);
                break;
        }
    }

    @NotNull
    private RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (radioGroup, buttonId) -> {
            int SORT_TYPE = getSortType(buttonId);
            SortTypeHelper.setSortType(requireActivity(), SORT_TYPE);
            mListener.onSortSelected(SORT_TYPE);
            this.dismiss();

        };
    }

    private int getSortType(int buttonId) {
        int SORT_TYPE = 0;
        switch (buttonId) {
            case R.id.radio_btn_hot:
                SORT_TYPE = HOT_SORT;
                break;
            case R.id.radio_btn_top:
                SORT_TYPE = TOP_SORT;
                break;
            case R.id.radio_btn_new:
                SORT_TYPE = NEW_SORT;
                break;
            case R.id.radio_btn_controversial:
                SORT_TYPE = CONTROVERSIAL_SORT;
                break;
        }
        return SORT_TYPE;
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (SortListener) parent;
        } else {
            mListener = (SortListener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public static String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    public interface SortListener {
        void onSortSelected(int type);
    }


}
