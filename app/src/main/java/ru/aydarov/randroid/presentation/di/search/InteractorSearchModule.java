package ru.aydarov.randroid.presentation.di.search;

import android.content.SharedPreferences;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauth;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearch;
import ru.aydarov.randroid.domain.search.SearchInteractor;
import ru.aydarov.randroid.domain.search.SearchInteractorImpl;
import ru.aydarov.randroid.domain.search.SearchRedditBoundaryCallback;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_SEARCH;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_SEARCH;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_PREF;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class InteractorSearchModule {


    @Provides
    @SearchScope
    SearchInteractor providePostInteractor(Lazy<RepositoryUserOauth> repositoryOauth, @Named(NO_OAUTH_REPO_SEARCH) Lazy<RepositorySearch> repositoryPost, @Named(OAUTH_REPO_SEARCH) Lazy<RepositorySearch> repositoryPostOauth, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences, Lazy<SearchRedditBoundaryCallback> redditBoundaryCallbackLazy) {
        return new SearchInteractorImpl(repositoryOauth, repositoryPostOauth, repositoryPost, sharedPreferences, redditBoundaryCallbackLazy);
    }

    @Provides
    @SearchScope
    SearchRedditBoundaryCallback provideRedditBoundaryCallback(Lazy<SearchInteractor> postInteractor) {
        return new SearchRedditBoundaryCallback(postInteractor);
    }
}
