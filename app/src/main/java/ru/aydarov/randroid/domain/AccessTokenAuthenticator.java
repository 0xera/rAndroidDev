package ru.aydarov.randroid.domain;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dagger.Lazy;
import okhttp3.Authenticator;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import ru.aydarov.randroid.data.util.RedditUtils;
import ru.aydarov.randroid.domain.token.TokenInteractor;

/**
 * @author Aydarov Askhar 2020
 */
public class AccessTokenAuthenticator implements Authenticator {

    private final Lazy<TokenInteractor> mTokenInteractor;

    public AccessTokenAuthenticator(Lazy<TokenInteractor> tokenInteractor) {
        mTokenInteractor = tokenInteractor;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) {
        if (response.code() == 401) {
            synchronized (this) {
                String header = response.request().header(RedditUtils.AUTHORIZATION_KEY);
                if (TextUtils.isEmpty(header))
                    return null;
                else {
                    String accessToken = header.substring(RedditUtils.AUTHORIZATION_BASE.length());
                    if (accessToken.equals(mTokenInteractor.get().getAccessTokenFromPreferences())) {
                        String newAccessToken = mTokenInteractor.get().refreshAccessToken();
                        if (TextUtils.isEmpty(newAccessToken))
                            return null;
                        else
                            response.request().newBuilder().headers(Headers.of(RedditUtils.getOAuthHeader(newAccessToken))).build();
                    } else {
                        return response.request().newBuilder().headers(Headers.of(RedditUtils.getOAuthHeader(accessToken))).build();
                    }
                }
            }
        }
        return null;
    }



}
