package ru.aydarov.randroid.presentation.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.FragmentSearchBinding;

/**
 * @author Aydarov Askhar 2020
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding mSearchFragmentBinding;
    private Toolbar mToolbar;
    private androidx.appcompat.widget.SearchView mSearchView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mSearchFragmentBinding = FragmentSearchBinding.inflate(inflater, container, false);
        initSearchView();
        initToolbar();
        return mSearchFragmentBinding.getRoot();
    }

    private void initSearchView() {
        mSearchView = mSearchFragmentBinding.toolbarLayout.searchView;
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                goSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.onActionViewExpanded();
        mSearchView.setOnCloseListener(() -> {
            requireActivity().finish();
            return true;
        });
        EditText searchEditText = mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        TypedValue typedValue = new TypedValue();
        requireActivity().getTheme().resolveAttribute(R.attr.hint_color, typedValue, true);
        searchEditText.setHintTextColor(typedValue.data);
    }

    private void initToolbar() {
        mToolbar = mSearchFragmentBinding.toolbarLayout.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> {
            mSearchView.clearFocus();
            requireActivity().finish();
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void goSearch(String query) {
        requireActivity().setResult(Activity.RESULT_OK, new Intent().putExtra(SearchActivity.SEARCH_KEY_EXTRA, query));
        requireActivity().finish();
    }
}
