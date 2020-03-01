package ru.aydarov.randroid.presentation.ui.searched;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.savedstate.SavedStateRegistryOwner;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.repo.post.NetworkState;
import ru.aydarov.randroid.domain.search.SearchInteractor;

import static ru.aydarov.randroid.domain.post.PostInteractorImpl.PAGE_SIZE;

/**
 * @author Aydarov Askhar 2020
 */
public class SearchedViewModel extends ViewModel {

    public static final String POST_KEY = "POST_Key";
    private Lazy<SearchInteractor> mInteractor;
    private SavedStateHandle mHandle;
    private MutableLiveData<String> mSortTypeLive = new MutableLiveData<>();
    private LiveData<ListingPost<RedditPost>> mResultLive = Transformations.map(mSortTypeLive, this::getRedditPostListing);

    private ListingPost<RedditPost> getRedditPostListing(String input) {
        String[] split = input.split(":", 2);
        if (split.length >= 2)
            return mInteractor.get().getPosts(split[0], PAGE_SIZE, split[1]);
        else
            return null;
    }

    private LiveData<NetworkState> mRefreshState = Transformations.switchMap(mResultLive, ListingPost::getRefreshState);
    private LiveData<NetworkState> mNetworkState = Transformations.switchMap(mResultLive, ListingPost::getNetworkState);
    private LiveData<PagedList<RedditPost>> mPosts = Transformations.switchMap(mResultLive, ListingPost<RedditPost>::getPagedList);

    public SearchedViewModel(SavedStateHandle handle, Lazy<SearchInteractor> interactor) {
        mInteractor = interactor;
        mHandle = handle;
        checkHandle();
    }

    private void checkHandle() {
        if (mHandle != null && mHandle.get(POST_KEY) != null) {
            mSortTypeLive.setValue(mHandle.get(POST_KEY));
        }
    }


    public void refresh() {
        if (mResultLive.getValue() != null) {
            mResultLive.getValue().getRefresh().invoke();
        }
    }


    public LiveData<PagedList<RedditPost>> getPosts() {
        return mPosts;
    }


    public LiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }


    public LiveData<NetworkState> getRefreshState() {
        return mRefreshState;
    }

    @Override
    protected void onCleared() {
        mInteractor.get().dispose();
        super.onCleared();
    }

    public void updateSortType(String sortType) {
        saveSortType(sortType);
        mSortTypeLive.setValue(sortType);

    }

    private void saveSortType(String sortType) {
        if (mHandle != null)
            mHandle.set(POST_KEY, sortType);
    }

    public Function0<Unit> getRetry() {
        return () -> {
            if (mResultLive.getValue() != null)
                return mResultLive.getValue().getRetry().invoke();
            return null;
        };
    }

    void setSortTypeLive(MutableLiveData<String> sortType) {
        mSortTypeLive = sortType;
    }

    LiveData<String> getSortTypeLive() {
        return mSortTypeLive;
    }

    public static class Factory {

        @Inject
        Lazy<SearchInteractor> mSearchInteractor;

        @Inject
        Factory() {
        }

        public AbstractSavedStateViewModelFactory create(SavedStateRegistryOwner owner) {
            return new AbstractSavedStateViewModelFactory(owner, null) {

                @NonNull
                @Override
                @SuppressWarnings("unchecked")
                protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
                    return (T) new SearchedViewModel(handle, mSearchInteractor);
                }
            };
        }
    }

}
