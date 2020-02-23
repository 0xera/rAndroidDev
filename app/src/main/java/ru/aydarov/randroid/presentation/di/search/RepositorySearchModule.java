package ru.aydarov.randroid.presentation.di.search;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchApi;
import ru.aydarov.randroid.data.repository.api.search.RedditSerachOauthAPI;
import ru.aydarov.randroid.data.repository.databases.RedditSearchDao;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearch;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearchImpl;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearchOauthImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_SEARCH;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_SEARCH_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_SEARCH;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_SEARCH_API;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class RepositorySearchModule {

    @Provides
    @SearchScope
    @Named(NO_OAUTH_REPO_SEARCH)
    RepositorySearch provideRepositoryPost(@Named(NO_OAUTH_SEARCH_API) Lazy<RedditSearchApi> redditAPI, Lazy<RedditSearchDao> redditDao) {
        return new RepositorySearchImpl(redditAPI, redditDao);
    }

    @Provides
    @SearchScope
    @Named(OAUTH_REPO_SEARCH)
    RepositorySearch provideRepositoryPostOauth(@Named(OAUTH_SEARCH_API) Lazy<RedditSerachOauthAPI> redditOauthAPI, Lazy<RedditSearchDao> redditDao) {
        return new RepositorySearchOauthImpl(redditOauthAPI, redditDao);
    }
}
