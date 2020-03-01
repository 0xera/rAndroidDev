package ru.aydarov.randroid.domain.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dagger.Lazy;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.token.TokenInteractor;

/**
 * @author Aydarov Askhar 2020
 */
public class AccessTokenAuthenticator implements Authenticator {

    private final Lazy<TokenInteractor> mTokenInteractor;
    private final HeaderGenerator mHeaderGenerator;

    public AccessTokenAuthenticator(Lazy<TokenInteractor> tokenInteractor, HeaderGenerator headerGenerator) {
        mTokenInteractor = tokenInteractor;
        mHeaderGenerator = headerGenerator;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) {
        if (response.code() == 401) {
            synchronized (this) {
                String header = response.request().header(RedditUtilsNet.AUTHORIZATION_KEY);
                if (header == null || header.length() == 0)
                    return null;
                else {
                    String accessToken = header.substring(RedditUtilsNet.AUTHORIZATION_BASE.length());
                    if (accessToken.equals(mTokenInteractor.get().getAccessTokenFromPreferences())) {
                        String newAccessToken = mTokenInteractor.get().refreshAccessToken();
                        if (newAccessToken == null || newAccessToken.length() == 0)
                            return null;
                        else {
                            return mHeaderGenerator.getHeaders(response.request().newBuilder(), newAccessToken);
                        }
                    } else {
                        return mHeaderGenerator.getHeaders(response.request().newBuilder(), accessToken);
                    }
                }
            }
        }
        return null;
    }
}
