package ru.aydarov.randroid.presentation.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.api.RedditAPI;
import ru.aydarov.randroid.data.repository.api.RedditOauthAPI;
import ru.aydarov.randroid.data.repository.api.RedditTokenAPI;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryOauth;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryOauthImpl;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPostImpl;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryTokenImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_API;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    RepositoryOauth provideRepositoryUser(@Named(OAUTH_API) Lazy<RedditOauthAPI> redditOauthAPI, Lazy<RedditDao> redditDao) {
        return new RepositoryOauthImpl(redditOauthAPI, redditDao);
    }

    @Provides
    @Singleton
    RepositoryToken provideRepositoryToken(@Named(TOKEN_API) Lazy<RedditTokenAPI> redditTokenAPI) {
        return new RepositoryTokenImpl(redditTokenAPI);
    }

    @Provides
    @Singleton
    RepositoryPost provideRepositoryPost(@Named(NO_OAUTH_API) Lazy<RedditAPI> redditAPI) {
        return new RepositoryPostImpl(redditAPI);
    }
}
