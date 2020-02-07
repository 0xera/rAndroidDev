package ru.aydarov.randroid.presentation.ui.post;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Lazy;
import ru.aydarov.randroid.domain.post.PostInteractor;

/**
 * @author Aydarov Askhar 2020
 */
public class PostListViewModel extends ViewModel {

    private final Lazy<PostInteractor> mInteractor;

    private PostListViewModel(Lazy<PostInteractor> interactor) {
        mInteractor = interactor;
    }

    void loadPosts(String sort) {
        mInteractor.get().loadPost();
    }

    public static class PostViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        @Inject
        Lazy<PostInteractor> mPostInteractor;

        @Inject
        PostViewModelFactory() {
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostListViewModel(mPostInteractor);
        }
    }
}
