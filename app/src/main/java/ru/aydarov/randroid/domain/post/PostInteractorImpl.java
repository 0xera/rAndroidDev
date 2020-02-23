package ru.aydarov.randroid.domain.post;

import android.content.SharedPreferences;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import dagger.Lazy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauth;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.util.TokensSharedHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class PostInteractorImpl implements PostInteractor {

    public static final int PAGE_SIZE = 25;
    private final Lazy<RepositoryPost> mRepositoryPost;
    private final Lazy<SharedPreferences> mSharedPreferences;
    private final Lazy<RepositoryPost> mRepositoryPostOauth;
    private final Lazy<PostRedditBoundaryCallback> mRedditBoundaryCallback;
    private Disposable mDisposable;

    public PostInteractorImpl(Lazy<RepositoryUserOauth> repositoryUserOauth, Lazy<RepositoryPost> repositoryPostOauth, Lazy<RepositoryPost> repositoryPost, Lazy<SharedPreferences> sharedPreferences, Lazy<PostRedditBoundaryCallback> redditBoundaryCallback) {
        mRepositoryPost = repositoryPost;
        mSharedPreferences = sharedPreferences;
        mRepositoryPostOauth = repositoryPostOauth;
        mRedditBoundaryCallback = redditBoundaryCallback;
    }

    @Override
    public void insertResultIntoDb(String sorType, RedditPost.RedditPostResponse response) {
        RepositoryPost currentRepo = getCurrentRepo();
        int start;
        List<RedditPost.RedditChild> childList = response.getData().getChildren();
        List<RedditPost> posts = new ArrayList<>();
        start = currentRepo.getNextIndexInSubreddit();
        for (int index = 0; index < childList.size(); index++) {
            RedditPost data = childList.get(index).getData();
            data.setIndexInResponse(start + index);
            data.setSortType(sorType);
            posts.add(data);

        }
        currentRepo.insertPost(posts);
    }

    private boolean isEmptyTokens() {
        return TextUtils.isEmpty(getToken(RedditUtilsNet.ACCESS_TOKEN_KEY)) || TextUtils.isEmpty(getToken(RedditUtilsNet.REFRESH_TOKEN_KEY));
    }

    private LiveData<NetworkState> refresh(String sortType) {
        MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        networkState.postValue(NetworkState.LOADING);
        mDisposable = loadPosts(sortType, null, PAGE_SIZE)
                .subscribe(response -> {
                    Completable.fromAction(getCurrentRepo()::deletePosts)
                            .andThen(Completable.fromAction(() -> insertResultIntoDb(sortType, response)))
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe();
                    networkState.postValue(NetworkState.LOADED);
                }, throwable -> networkState.postValue(NetworkState.error(throwable.getMessage())));
        return networkState;
    }


    @NotNull
    private String getToken(String accessTokenKey) {
        return mSharedPreferences.get().getString(accessTokenKey, TokensSharedHelper.NONE);
    }

    private RepositoryPost getCurrentRepo() {
        if (isEmptyTokens())
            return mRepositoryPost.get();
        else return mRepositoryPostOauth.get();
    }

    @Override
    public ListingPost<RedditPost> getPosts(String sortType, int pageSize) {
        mRedditBoundaryCallback.get().setSortType(sortType);
        mRedditBoundaryCallback.get().setPageSize(pageSize);
        MutableLiveData<Void> refreshTrigger = new MutableLiveData<>();
        LiveData<NetworkState> refreshState = Transformations.switchMap(refreshTrigger, input -> refresh(sortType));

        DataSource.Factory<Integer, RedditPost> postsDb;
        postsDb = getCurrentRepo().getPostsDb();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setPrefetchDistance(1)
                .build();
        LiveData<PagedList<RedditPost>> pagedListLiveData = new LivePagedListBuilder<>(postsDb, config)
                .setBoundaryCallback(mRedditBoundaryCallback.get()).build();

        return new ListingPost<RedditPost>(pagedListLiveData, mRedditBoundaryCallback.get().getNetworkState(), refreshState, () -> {
            refreshTrigger.setValue(null);
            return null;
        }, () -> {
            mRedditBoundaryCallback.get().getPagingRequestHelper().retryAllFailed();
            return null;
        });
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String lastItem, int pageSize) {
        Flowable<RedditPost.RedditPostResponse> responseFlowable;
        if (TextUtils.isEmpty(lastItem)) {
            responseFlowable = getCurrentRepo().loadPosts(sortType, getToken(RedditUtilsNet.ACCESS_TOKEN_KEY), pageSize);
        } else {
            responseFlowable = getCurrentRepo().loadPosts(sortType, lastItem, getToken(RedditUtilsNet.ACCESS_TOKEN_KEY), pageSize);
        }

        return responseFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

    }

    @Override
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        mRedditBoundaryCallback.get().dispose();
    }
}
