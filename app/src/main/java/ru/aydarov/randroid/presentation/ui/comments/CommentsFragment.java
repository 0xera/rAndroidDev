package ru.aydarov.randroid.presentation.ui.comments;

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
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.databinding.CommentFragmentBinding;
import ru.aydarov.randroid.presentation.common.App;
import ru.aydarov.randroid.presentation.common.INavigatorSource;
import ru.aydarov.randroid.presentation.ui.adapters.CommentsAdapter;
import ru.aydarov.randroid.presentation.ui.bottom_sheet.SortBottomSheetFragment;
import ru.aydarov.randroid.presentation.ui.searched.SearchedFragment;
import ru.aydarov.randroid.presentation.ui.view.SwipeRefreshLayout;
import ru.aydarov.randroid.presentation.util.PostBindingHelper;

import static ru.aydarov.randroid.data.util.Constants.REQUEST_TRANSITION;

public class CommentsFragment extends Fragment implements SortBottomSheetFragment.SortListener, INavigatorSource {

    private static final String REFRESH = "key_ref";
    private static final String RECYCLER_VIEW_POSITION_STATE = "key_pos";
    public static final String POST_KEY = "post_key";
    //  private CommentsViewModel mViewModel;
    private CommentFragmentBinding mCommentBinding;
    private Toolbar mToolbar;
    private SortBottomSheetFragment mSortBottomSheetFragment;
    private RecyclerView mRecyclerView;
    private CommentsAdapter mAdapter;
    private boolean toScrollToTop = false;
    private String mSortType = RedditUtilsNet.HOT;
    @Inject
    CommentsViewModel.Factory mFactoryViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private RedditPost mPost;
    private CommentsViewModel mViewModel;
    private View.OnClickListener mOnClickMediaListener;
    private View.OnClickListener mOnClickShareListener;

    public static SearchedFragment newInstance(Bundle bundle) {
        SearchedFragment searchedFragment = new SearchedFragment();
        searchedFragment.setArguments(bundle);
        return searchedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mSortBottomSheetFragment = new SortBottomSheetFragment();
        App.getCommentComponent().inject(this);
        if (getArguments() != null) {
            mPost = (RedditPost) getArguments().getSerializable(POST_KEY);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mCommentBinding = CommentFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this, mFactoryViewModel.create(this)).get(CommentsViewModel.class);
        PostBindingHelper.binding(this, mCommentBinding.postItem, mPost, mOnClickMediaListener, mOnClickShareListener, null, false);
        mCommentBinding.postItem.ivComments.setVisibility(View.GONE);
        initRecyclerView(savedInstanceState);
        initToolbar(savedInstanceState);
        return mCommentBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(REFRESH, true);
        if (mLinearLayoutManager != null) {
            outState.putInt(RECYCLER_VIEW_POSITION_STATE, mLinearLayoutManager.findFirstVisibleItemPosition());
        }
        super.onSaveInstanceState(outState);
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        mSwipeRefreshLayout = mCommentBinding.swipeRefreshLayout;
        mRecyclerView = mCommentBinding.commentsRecyclerView;
        mLinearLayoutManager = new LinearLayoutManager(requireContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (savedInstanceState != null && savedInstanceState.getInt(RECYCLER_VIEW_POSITION_STATE) > 0) {
            mRecyclerView.scrollToPosition(savedInstanceState.getInt(RECYCLER_VIEW_POSITION_STATE));
        }
        mAdapter = new CommentsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        configView();
    }


    private void configView() {
        mViewModel.getComments().observe(getViewLifecycleOwner(), networkCommentResult -> {
            switch (networkCommentResult.getStatus()) {
                case NONE:
                    break;
                case LOADING:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case SUCCESS:
                    mAdapter.submitList(networkCommentResult.getCommentList());
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case FAILED:
                    mAdapter.setError();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;

            }

        });


    }

    private void initToolbar(Bundle savedInstanceState) {
        mToolbar = mCommentBinding.toolbarLayout.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null || !savedInstanceState.getBoolean(REFRESH))
            mToolbar.post(this::setSortType);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_comment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort_post_list_fragment) {
            checkBeforeShowBottomSheet();
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

        mViewModel.update(mPost.getId() + ":" + mSortType);
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
