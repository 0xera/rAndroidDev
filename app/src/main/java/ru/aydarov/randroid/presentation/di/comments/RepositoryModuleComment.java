package ru.aydarov.randroid.presentation.di.comments;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentAPI;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentOauthAPI;
import ru.aydarov.randroid.data.repository.repo.comment.RepositoryComment;
import ru.aydarov.randroid.data.repository.repo.comment.RepositoryCommentImpl;
import ru.aydarov.randroid.data.repository.repo.comment.RepositoryCommentOauthImpl;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_COMMENT_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_REPO_COMMENT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_COMMENT_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_REPO_COMMENT;

@Module
public class RepositoryModuleComment {
    @Provides
    @CommentScope
    @Named(NO_OAUTH_REPO_COMMENT)
    RepositoryComment provideRepositoryComment(@Named(NO_OAUTH_COMMENT_API) Lazy<RedditCommentAPI> redditAPI) {
        return new RepositoryCommentImpl(redditAPI);
    }

    @Provides
    @CommentScope
    @Named(OAUTH_REPO_COMMENT)
    RepositoryComment provideRepositoryCommentOauth(@Named(OAUTH_COMMENT_API) Lazy<RedditCommentOauthAPI> redditOauthAPI) {
        return new RepositoryCommentOauthImpl(redditOauthAPI);
    }
}
