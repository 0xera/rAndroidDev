package ru.aydarov.randroid.presentation.di;

import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryOauth;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.domain.post.PostInteractor;
import ru.aydarov.randroid.domain.post.PostInteractorImpl;
import ru.aydarov.randroid.domain.token.TokenInteractor;
import ru.aydarov.randroid.domain.token.TokenInteractorImpl;
import ru.aydarov.randroid.domain.user.UserInteractor;
import ru.aydarov.randroid.domain.user.UserInteractorImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_PREF;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class InteractorModule {

    @Provides
    @Singleton
    UserInteractor provideUserInteractor(Lazy<RepositoryOauth> repositoryUser) {
        return new UserInteractorImpl(repositoryUser);
    }

    @Provides
    @Singleton
    TokenInteractor provideTokenInteractor(Lazy<RepositoryToken> repositoryToken, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences) {
        return new TokenInteractorImpl(repositoryToken, sharedPreferences);
    }

    @Provides
    @Singleton
    PostInteractor providePostInteractor(Lazy<RepositoryOauth> repositoryOauth, Lazy<RepositoryPost> repositoryPost, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences) {
        return new PostInteractorImpl(repositoryOauth,repositoryPost, sharedPreferences);
    }
}
