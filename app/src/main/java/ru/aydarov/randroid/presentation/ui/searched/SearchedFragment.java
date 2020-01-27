package ru.aydarov.randroid.presentation.ui.searched;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.SearchedFragmentBinding;
import ru.aydarov.randroid.presentation.common.SingleActivity;
import ru.aydarov.randroid.presentation.ui.bottom_sheet.SortBottomSheetFragment;
import ru.aydarov.randroid.presentation.ui.search.SearchActivity;

import static android.app.Activity.RESULT_OK;

public class SearchedFragment extends Fragment implements SortBottomSheetFragment.SortListener {

    private static int SORT_TYPE = 0;
    private SearchedViewModel mViewModel;
    private SearchedFragmentBinding mPostListBinding;
    private Toolbar mToolbar;
    private SortBottomSheetFragment mSortBottomSheetFragment;
    private RecyclerView mRecyclerView;

    public static SearchedFragment newInstance(Bundle bundle) {
        SearchedFragment searchedFragment = new SearchedFragment();
        searchedFragment.setArguments(bundle);
        return searchedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mSortBottomSheetFragment = new SortBottomSheetFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPostListBinding = SearchedFragmentBinding.inflate(inflater, container, false);
        initToolbar();
        initRecyclerView();
        return mPostListBinding.getRoot();
    }

    private void initRecyclerView() {
        mRecyclerView = mPostListBinding.postListRecyclerView;
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
                                     @NonNull
                                     @Override
                                     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                         return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.support_simple_spinner_dropdown_item, parent, false));
                                     }

                                     @Override
                                     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                                     }

                                     @Override
                                     public int getItemCount() {
                                         return 50;
                                     }

                                     class ViewHolder extends RecyclerView.ViewHolder {
                                         public ViewHolder(@NonNull View itemView) {
                                             super(itemView);
                                             TextView viewById = itemView.findViewById(android.R.id.text1);
                                             viewById.setText("blalvlalvalva");
                                         }
                                     }
                                 }


        );
    }

    private void initToolbar() {
        mToolbar = mPostListBinding.toolbarLayout.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchedViewModel.class);
        if (getArguments() != null) {
            setToolbarTitle(getArguments().getString(SearchActivity.SEARCH_KEY_EXTRA));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_post, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_post_list_fragment:
                checkBeforeShowBottomSheet();
                return true;
            case R.id.action_search_post_list_fragment:
                startActivityForResult(SearchActivity.newIntent(requireActivity()), SearchActivity.SEARCH_REQUEST_CODE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkBeforeShowBottomSheet() {
        Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(SortBottomSheetFragment.getFragmentTag());
        if (fragmentByTag != null && !fragmentByTag.isAdded()) {
            showSortBottomSheet();
        } else if (fragmentByTag == null) {
            showSortBottomSheet();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SearchActivity.SEARCH_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            ((SingleActivity) requireActivity()).navigateFromSearchedToSelf(data.getExtras());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showSortBottomSheet() {
        mSortBottomSheetFragment.show(getChildFragmentManager(), SortBottomSheetFragment.getFragmentTag());
    }

    @Override
    public void onSortSelected(int type) {
        SORT_TYPE = type;


    }

    private void setToolbarTitle(String query) {
        mToolbar.setTitle(query);

    }

}
