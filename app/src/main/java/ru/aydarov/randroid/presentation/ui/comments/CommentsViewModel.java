package ru.aydarov.randroid.presentation.ui.comments;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;
import dagger.Lazy;
import ru.aydarov.randroid.domain.comments.CommentInteractor;
import ru.aydarov.randroid.domain.comments.NetworkCommentResult;

/**
 * @author Aydarov Askhar 2020
 */
public class CommentsViewModel extends ViewModel {

    private final Lazy<CommentInteractor> mInteractor;
    private final SavedStateHandle mHandle;
    private MutableLiveData<String> mSortTypeAndIdLive = new MutableLiveData<>();
    private LiveData<NetworkCommentResult> mComments = Transformations.switchMap(mSortTypeAndIdLive, this::getRedditPostComments);

    private MutableLiveData<NetworkCommentResult> getRedditPostComments(String input) {
        String[] split = input.split(":", 3);
        if (split.length >= 3)
            return mInteractor.get().getCommentsBySingleThread(split[0], split[1], split[3]);
        else if (split.length >= 2)
            return mInteractor.get().getComments(split[0], split[1]);
        return null;
    }

    private CommentsViewModel(SavedStateHandle handle, Lazy<CommentInteractor> interactor) {
        mInteractor = interactor;
        mHandle = handle;
    }


    public LiveData<NetworkCommentResult> getComments() {
        return mComments;
    }

    public void update(String value) {
        mSortTypeAndIdLive.setValue(value);
    }


    public static class Factory {

        @Inject
        Lazy<CommentInteractor> mPostInteractor;

        @Inject
        Factory() {
        }

        public AbstractSavedStateViewModelFactory create(SavedStateRegistryOwner owner) {
            return new AbstractSavedStateViewModelFactory(owner, null) {

                @NonNull
                @Override
                @SuppressWarnings("unchecked")
                protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
                    return (T) new CommentsViewModel(handle, mPostInteractor);
                }
            };
        }
    }
}
