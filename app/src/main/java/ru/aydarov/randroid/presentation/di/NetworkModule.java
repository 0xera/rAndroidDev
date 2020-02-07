package ru.aydarov.randroid.presentation.di;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.aydarov.randroid.data.repository.api.RedditAPI;
import ru.aydarov.randroid.data.repository.api.RedditOauthAPI;
import ru.aydarov.randroid.data.repository.api.RedditTokenAPI;
import ru.aydarov.randroid.data.util.RedditUtils;
import ru.aydarov.randroid.domain.AccessTokenAuthenticator;
import ru.aydarov.randroid.domain.token.TokenInteractor;

import static ru.aydarov.randroid.presentation.di.NamesUtil.AUTHENTICATOR;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_AUTHENTICATOR;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_RETROFIT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_RETROFIT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_API;

/**
 * @author Aydarov Askhar 2020
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Named(NO_AUTHENTICATOR)
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    @Named(AUTHENTICATOR)
    OkHttpClient provideOkHttpClientAuth(AccessTokenAuthenticator authenticator, HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .authenticator(authenticator)
                .addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    AccessTokenAuthenticator provideAuthenticator(Lazy<TokenInteractor> tokenInteractor) {
        return new AccessTokenAuthenticator(tokenInteractor);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    @Named(NO_OAUTH_RETROFIT)
    Retrofit provideRetrofitBase(@Named(NO_AUTHENTICATOR) OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(RedditUtils.API_BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(OAUTH_RETROFIT)
    Retrofit provideRetrofitOauth(@Named(AUTHENTICATOR) OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(RedditUtils.OAUTH_API_BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(TOKEN_API)
    RedditTokenAPI provideTokenApi(@Named(NO_OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditTokenAPI.class);

    }

    @Provides
    @Singleton
    @Named(NO_OAUTH_API)
    RedditAPI provideApi(@Named(NO_OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditAPI.class);

    }


    @Provides
    @Singleton
    @Named(OAUTH_API)
    RedditOauthAPI provideUserApi(@Named(OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditOauthAPI.class);

    }

}
