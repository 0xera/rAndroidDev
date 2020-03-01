package ru.aydarov.randroid.presentation.ui.web;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Lazy;
import ru.aydarov.randroid.domain.token.TokenInteractor;

/**
 * @author Aydarov Askhar 2020
 */
public class WebViewViewModel extends ViewModel {

    private static final String TAG = "WebViewViewModel";
    private final Lazy<TokenInteractor> mTokenInteractor;
    private MutableLiveData<State> mState = new MutableLiveData<>(State.NONE);


    public WebViewViewModel(Lazy<TokenInteractor> tokenInteractor) {
        mTokenInteractor = tokenInteractor;
    }

    boolean checkUrlAndGetTokens(String url) {
        return !mTokenInteractor.get().checkUrlAndGetTokens(mState, url);
    }

    String getUrl() {
        return mTokenInteractor.get().getUrl();
    }

    void dispose() {
        mTokenInteractor.get().dispose();
    }

    MutableLiveData<State> getState() {
        return mState;
    }


    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }


    public enum State {
        NONE, SUCCESS, DENIED, ERROR
    }

    public static class WebViewViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        @Inject
        Lazy<TokenInteractor> mTokenInteractor;

        @Inject
        WebViewViewModelFactory() {
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new WebViewViewModel(mTokenInteractor);
        }
    }
}

