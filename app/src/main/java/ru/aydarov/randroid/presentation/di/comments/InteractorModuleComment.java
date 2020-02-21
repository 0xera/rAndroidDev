package ru.aydarov.randroid.presentation.di.comments;

import android.content.SharedPreferences;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.repo.comment.RepositoryComment;
import ru.aydarov.randroid.domain.comments.CommentInteractor;
import ru.aydarov.randroid.domain.comments.CommentInteractorImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_COMMENT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_COMMENT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_PREF;

/**
 * @author Aydarov Askhar 2020
 */
@Module
public class InteractorModuleComment {

    @Provides
    @CommentScope
    CommentInteractor provideCommentInteractor(@Named(NO_OAUTH_REPO_COMMENT) Lazy<RepositoryComment> repositoryComment, @Named(OAUTH_REPO_COMMENT) Lazy<RepositoryComment> repositoryCommentOauth, @Named(TOKEN_PREF) Lazy<SharedPreferences> sharedPreferences) {
        return new CommentInteractorImpl(repositoryComment, repositoryCommentOauth, sharedPreferences);
    }

}
