package ru.aydarov.randroid.domain.token;

import android.content.SharedPreferences;
import android.net.Uri;

import java.io.IOException;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import dagger.Lazy;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.model.Token;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.data.util.RedditUtils;
import ru.aydarov.randroid.data.util.TokensSharedHelper;
import ru.aydarov.randroid.presentation.ui.web.WebViewViewModel;

/**
 * @author Aydarov Askhar 2020
 */
public class TokenInteractorImpl implements TokenInteractor {
    public static final String CODE_URL_1 = "&code=";
    public static final String CODE_URL_2 = "?code=";
    public static final String ERROR_ACCESS_DENIED = "error=access_denied";
    private final Lazy<RepositoryToken> mRepositoryToken;
    private final Lazy<SharedPreferences> mPreferences;
    private Uri mUri;
    private Disposable mDisposable;

    public TokenInteractorImpl(Lazy<RepositoryToken> repositoryToken, Lazy<SharedPreferences> preferences) {
        mRepositoryToken = repositoryToken;
        mPreferences = preferences;
    }

    @Override
    public Single<Token> getAccessToken() {
        return null;
    }

    public String getUrl() {
        Uri baseUri = Uri.parse(RedditUtils.OAUTH_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter(RedditUtils.CLIENT_ID_KEY, RedditUtils.CLIENT_ID);
        uriBuilder.appendQueryParameter(RedditUtils.RESPONSE_TYPE_KEY, RedditUtils.RESPONSE_TYPE);
        uriBuilder.appendQueryParameter(RedditUtils.STATE_KEY, RedditUtils.STATE);
        uriBuilder.appendQueryParameter(RedditUtils.REDIRECT_URI_KEY, RedditUtils.REDIRECT_URI);
        uriBuilder.appendQueryParameter(RedditUtils.DURATION_KEY, RedditUtils.DURATION);
        uriBuilder.appendQueryParameter(RedditUtils.SCOPE_KEY, RedditUtils.SCOPE);
        return uriBuilder.toString();
    }

    @Override
    public boolean checkUrlAndGetTokens(MutableLiveData<WebViewViewModel.State> state, String url) {
        if (url.contains(CODE_URL_1) || url.contains(CODE_URL_2)) {
            mUri = Uri.parse(url);
            String state_key = mUri.getQueryParameter(RedditUtils.STATE_KEY);
            if (state_key != null && state_key.equals(RedditUtils.STATE))
                return getAccessAndRefreshTokens(state);
        } else if (url.contains(ERROR_ACCESS_DENIED)) {
            state.postValue(WebViewViewModel.State.DENIED);
            return false;
        }
        return false;
    }

    private boolean getAccessAndRefreshTokens(MutableLiveData<WebViewViewModel.State> state) {
        Map<String, String> params = RedditUtils.getParamsAuth(mUri);
        mDisposable = mRepositoryToken.get().getToken(RedditUtils.getHttpBasicAuthHeader(), params)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(token -> {
                    saveToken(token);
                    state.postValue(WebViewViewModel.State.SUCCESS);

                }, throwable -> state.postValue(WebViewViewModel.State.ERROR));
        return true;
    }

    private void saveToken(Token token) {
        mPreferences.get().edit().putString(RedditUtils.ACCESS_TOKEN_KEY, token.getAccessToken()).apply();
        mPreferences.get().edit().putString(RedditUtils.REFRESH_TOKEN_KEY, token.getRefreshToken()).apply();
    }

    private void saveAccessToken(Token token) {
        mPreferences.get().edit().putString(RedditUtils.ACCESS_TOKEN_KEY, token.getAccessToken()).apply();
    }

    @Override
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public String getAccessTokenFromPreferences() {
        return mPreferences.get().getString(RedditUtils.ACCESS_TOKEN_KEY, TokensSharedHelper.NONE);
    }

    @Override
    public String getRefreshTokenFromPreferences() {
        return mPreferences.get().getString(RedditUtils.REFRESH_TOKEN_KEY, TokensSharedHelper.NONE);

    }

    @Override
    public String refreshAccessToken() {
        try {
            retrofit2.Response<Token> response = mRepositoryToken.get().getTokenCall(RedditUtils.getHttpBasicAuthHeader(), RedditUtils.getParamsRefresh(getRefreshTokenFromPreferences())).execute();
            if (response.isSuccessful() && response.body() != null) {
                saveAccessToken(response.body());
                return response.body().getAccessToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TokensSharedHelper.NONE;
    }


}
