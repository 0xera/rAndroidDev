package ru.aydarov.randroid.presentation.di.app;

import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauth;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.domain.post.PostInteractor;
import ru.aydarov.randroid.domain.post.PostInteractorImpl;
import ru.aydarov.randroid.domain.post.PostRedditBoundaryCallback;
import ru.aydarov.randroid.domain.token.TokenInteractor;
import ru.aydarov.randroid.domain.token.TokenInteractorImpl;
import ru.aydarov.randroid.domain.user.UserInteractor;
import ru.aydarov.randroid.domain.user.UserInteractorImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_POST;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_POST;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_PREF;

/**
 * @author Aydarov Askhar 2020
 */
@Module
class InteractorModule {

    @Provides
    @Singleton
    UserInteractor provideUserInteractor(Lazy<RepositoryUserOauth> repositoryUser) {
        return new UserInteractorImpl(repositoryUser);
    }

    @Provides
    @Singleton
    TokenInteractor provideTokenInteractor(Lazy<RepositoryToken> repositoryToken, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences) {
        return new TokenInteractorImpl(repositoryToken, sharedPreferences);
    }

    @Provides
    @Singleton
    PostInteractor providePostInteractor(Lazy<RepositoryUserOauth> repositoryOauth, @Named(NO_OAUTH_REPO_POST) Lazy<RepositoryPost> repositoryPost, @Named(OAUTH_REPO_POST) Lazy<RepositoryPost> repositoryPostOauth, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences, Lazy<PostRedditBoundaryCallback> redditBoundaryCallbackLazy) {
        return new PostInteractorImpl(repositoryOauth, repositoryPostOauth, repositoryPost, sharedPreferences, redditBoundaryCallbackLazy);
    }

    @Provides
    @Singleton
    PostRedditBoundaryCallback provideRedditBoundaryCallback(Lazy<PostInteractor> postInteractor) {
        return new PostRedditBoundaryCallback(postInteractor);
    }

}
