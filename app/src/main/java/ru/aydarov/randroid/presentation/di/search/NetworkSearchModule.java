package ru.aydarov.randroid.presentation.di.search;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchApi;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchOauthAPI;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_RETROFIT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_SEARCH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_RETROFIT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_SEARCH_API;

/**
 * @author Aydarov Askhar 2020
 */

@Module
public class NetworkSearchModule {


    @Provides
    @SearchScope
    @Named(NO_OAUTH_SEARCH_API)
    RedditSearchApi provideSearchApi(@Named(NO_OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditSearchApi.class);

    }


    @Provides
    @SearchScope
    @Named(OAUTH_SEARCH_API)
    RedditSearchOauthAPI provideSearchOauthApi(@Named(OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditSearchOauthAPI.class);

    }

}
