package ru.aydarov.randroid.domain.post;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import dagger.Lazy;
import io.reactivex.disposables.Disposable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.paging_helper.PagingRequestHelper;
import ru.aydarov.randroid.domain.paging_helper.PagingRequestHelperExtKt;

/**
 * @author Aydarov Askhar 2020
 */
public class PostRedditBoundaryCallback extends PagedList.BoundaryCallback<RedditPost> {

    private final Lazy<PostInteractor> mInteractor;
    private LiveData<NetworkState> mNetworkState;
    private String mSortType = RedditUtilsNet.HOT;


    private String mSearchQuery;
    private int mPageSize = 25;
    private Disposable mDisposable;

    public PostRedditBoundaryCallback(Lazy<PostInteractor> interactor) {

        mInteractor = interactor;
    }

    private PagingRequestHelper mPagingRequestHelper;

    public LiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }

    public PagingRequestHelper getPagingRequestHelper() {
        return mPagingRequestHelper;
    }

    {
        mPagingRequestHelper = new PagingRequestHelper(Executors.newSingleThreadExecutor());
        mNetworkState = PagingRequestHelperExtKt.createStatusLiveData(mPagingRequestHelper);
    }


    @Override
    public void onZeroItemsLoaded() {
        mPagingRequestHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL,
                callback -> mDisposable = mInteractor.get().loadPosts(mSortType, null, mPageSize, mSearchQuery)
                        .subscribe(response -> {
                            mInteractor.get().insertResultIntoDb(mSortType, response, mSearchQuery);
                            callback.recordSuccess();
                        }, callback::recordFailure));
        super.onZeroItemsLoaded();

    }

    @Override
    public void onItemAtEndLoaded(@NonNull RedditPost itemAtEnd) {
        mPagingRequestHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER,
                callback -> mDisposable = mInteractor.get().loadPosts(mSortType, itemAtEnd.getName(), mPageSize, mSearchQuery)
                        .subscribe(response -> {
                            mInteractor.get().insertResultIntoDb(mSortType, response, mSearchQuery);
                            callback.recordSuccess();
                        }, callback::recordFailure));
        super.onItemAtEndLoaded(itemAtEnd);
    }


    void setSortType(String sortType) {
        mSortType = sortType;

    }

    void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
    }

    void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
