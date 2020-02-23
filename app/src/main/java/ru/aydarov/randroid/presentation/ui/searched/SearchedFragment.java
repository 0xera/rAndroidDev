package ru.aydarov.randroid.presentation.ui.searched;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.databinding.SearchedFragmentBinding;
import ru.aydarov.randroid.domain.util.SortTypeHelper;
import ru.aydarov.randroid.presentation.common.App;
import ru.aydarov.randroid.presentation.common.INavigatorSource;
import ru.aydarov.randroid.presentation.ui.adapters.PostAdapter;
import ru.aydarov.randroid.presentation.ui.bottom_sheet.SortBottomSheetFragment;
import ru.aydarov.randroid.presentation.ui.search.SearchActivity;
import ru.aydarov.randroid.presentation.ui.view.SwipeRefreshLayout;

import static android.app.Activity.RESULT_OK;
import static ru.aydarov.randroid.data.util.Constants.REQUEST_TRANSITION;
import static ru.aydarov.randroid.presentation.ui.search.SearchActivity.SEARCH_KEY_EXTRA;

public class SearchedFragment extends Fragment implements SortBottomSheetFragment.SortListener, INavigatorSource {

    private static final String REFRESH = "key_ref";
    private static final String RECYCLER_VIEW_POSITION_STATE = "key_pos";
    private SearchedViewModel mViewModel;
    private SearchedFragmentBinding mPostListBinding;
    private Toolbar mToolbar;
    private SortBottomSheetFragment mSortBottomSheetFragment;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private String mSortType = RedditUtilsNet.HOT;
    private String mQuery;

    @Inject
    SearchedViewModel.Factory mFactoryViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private byte isChangeSort = 1;

    public static SearchedFragment newInstance(Bundle bundle) {
        SearchedFragment searchedFragment = new SearchedFragment();
        searchedFragment.setArguments(bundle);
        return searchedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mSortBottomSheetFragment = new SortBottomSheetFragment();
        App.getSearchComponent().inject(this);
        if (getArguments() != null) {
            mQuery = getArguments().getString(SEARCH_KEY_EXTRA);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPostListBinding = SearchedFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this, mFactoryViewModel.create(this)).get(SearchedViewModel.class);
        initRecyclerView(savedInstanceState);
        initToolbar();
        setToolbarTitle(mQuery);
        return mPostListBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(REFRESH, true);
        if (mLinearLayoutManager != null) {
            outState.putInt(RECYCLER_VIEW_POSITION_STATE, mLinearLayoutManager.findFirstVisibleItemPosition());
        } else if (mStaggeredGridLayoutManager != null) {
            int[] into = new int[2];
            outState.putInt(RECYCLER_VIEW_POSITION_STATE,
                    mStaggeredGridLayoutManager.findFirstVisibleItemPositions(into)[0]);
        }
        super.onSaveInstanceState(outState);
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        mSwipeRefreshLayout = mPostListBinding.swipeRefreshLayout;
        mRecyclerView = mPostListBinding.postListRecyclerView;
        if (getResources().getBoolean(R.bool.is_horizontal)) {
            mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mLayoutManager = mStaggeredGridLayoutManager;
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            mLinearLayoutManager = new LinearLayoutManager(requireContext());
            mLayoutManager = mLinearLayoutManager;
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        if (savedInstanceState != null && savedInstanceState.getInt(RECYCLER_VIEW_POSITION_STATE) > 0) {
            mRecyclerView.scrollToPosition(savedInstanceState.getInt(RECYCLER_VIEW_POSITION_STATE));
        }
        mAdapter = new PostAdapter(mViewModel.getRetry(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemViewCacheSize(25);
        configView();
    }

    private void configView() {
        mViewModel.getPosts().observe(getViewLifecycleOwner(), redditPosts -> {
            mAdapter.submitList(redditPosts);
            if (!mSwipeRefreshLayout.isRefreshing() && isChangeSort > 0) {
                mRecyclerView.scrollToPosition(0);
                isChangeSort--;
            }
        });
        mViewModel.getNetworkState().observe(getViewLifecycleOwner(),
                networkState -> mAdapter.setNetworkState(networkState));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mAdapter.swipeRefreshing(false);
            mViewModel.refresh();
        });
        mViewModel.getRefreshState().observe(getViewLifecycleOwner(),
                networkState -> mSwipeRefreshLayout.setRefreshing(networkState.getStatus() == NetworkState.Status.RUNNING));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.getBoolean(REFRESH))
            mToolbar.post(() -> onSortSelected(SortTypeHelper.getSortType(requireActivity())));
        super.onActivityCreated(savedInstanceState);
    }

    private void initToolbar() {
        mToolbar = mPostListBinding.toolbarLayout.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

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
            ((SearchedActivity) requireActivity()).navigateFromSearchedToSelf(data.getExtras());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showSortBottomSheet() {
        mSortBottomSheetFragment.show(getChildFragmentManager(), SortBottomSheetFragment.getFragmentTag());
    }

    @Override
    public void onSortSelected(int type) {
        switch (type) {
            case 0:
                setSortType();
                mSortType = RedditUtilsNet.HOT;
                break;
            case 1:
                setSortType();
                mSortType = RedditUtilsNet.TOP;
                break;
            case 2:
                setSortType();
                mSortType = RedditUtilsNet.NEW;
                break;
            case 3:
                setSortType();
                mSortType = RedditUtilsNet.CONTROVERSIAL;
                break;
        }

    }

    private void setSortType() {
        mViewModel.updateSortType(mSortType + ":" + mQuery);
        mViewModel.refresh();
        isChangeSort = 1;

    }


    private void setToolbarTitle(String query) {
        mToolbar.setTitle(query);

    }

    @Override
    public void onDestroy() {
        App.clearSearchComponent();
        super.onDestroy();
    }

    @Override
    public void navigateToSourceViewActivity(View view, Intent intent) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), view, getString(R.string.src_transition));
        requireActivity().startActivityFromFragment(this, intent, REQUEST_TRANSITION, activityOptionsCompat.toBundle());
    }

    @Override
    public void onShare(Intent intent) {
        startActivity(intent);
    }

}
