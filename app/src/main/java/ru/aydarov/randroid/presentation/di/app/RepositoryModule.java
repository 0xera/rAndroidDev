package ru.aydarov.randroid.presentation.di.app;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.api.post.RedditPostAPI;
import ru.aydarov.randroid.data.repository.api.post.RedditPostOauthAPI;
import ru.aydarov.randroid.data.repository.api.token.RedditTokenAPI;
import ru.aydarov.randroid.data.repository.api.user.RedditUserApi;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauth;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauthImpl;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPostImpl;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPostOauthImpl;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryTokenImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_POST_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_POST;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_POST_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_POST;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.USER_API;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    RepositoryUserOauth provideRepositoryUser(@Named(USER_API) Lazy<RedditUserApi> redditTokenAPI, Lazy<RedditDao> redditDao) {
        return new RepositoryUserOauthImpl(redditTokenAPI, redditDao);
    }

    @Provides
    @Singleton
    RepositoryToken provideRepositoryToken(@Named(TOKEN_API) Lazy<RedditTokenAPI> redditTokenAPI) {
        return new RepositoryTokenImpl(redditTokenAPI);
    }

    @Provides
    @Singleton
    @Named(NO_OAUTH_REPO_POST)
    RepositoryPost provideRepositoryPost(@Named(NO_OAUTH_POST_API) Lazy<RedditPostAPI> redditAPI, Lazy<RedditDao> redditDao) {
        return new RepositoryPostImpl(redditAPI, redditDao);
    }

    @Provides
    @Singleton
    @Named(OAUTH_REPO_POST)
    RepositoryPost provideRepositoryPostOauth(@Named(OAUTH_POST_API) Lazy<RedditPostOauthAPI> redditOauthAPI, Lazy<RedditDao> redditDao) {
        return new RepositoryPostOauthImpl(redditOauthAPI, redditDao);
    }
}
