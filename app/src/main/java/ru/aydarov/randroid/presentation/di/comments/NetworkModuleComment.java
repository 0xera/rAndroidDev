package ru.aydarov.randroid.presentation.di.comments;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentAPI;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentOauthAPI;

import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_COMMENT_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.NO_OAUTH_RETROFIT;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_COMMENT_API;
import static ru.aydarov.randroid.presentation.di.NamesUtil.OAUTH_RETROFIT;

/**
 * @author Aydarov Askhar 2020
 */

@Module
public class NetworkModuleComment {


    @Provides
    @CommentScope
    @Named(NO_OAUTH_COMMENT_API)
    RedditCommentAPI providePostApi(@Named(NO_OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditCommentAPI.class);

    }

    @Provides
    @CommentScope
    @Named(OAUTH_COMMENT_API)
    RedditCommentOauthAPI providePostOauthApi(@Named(OAUTH_RETROFIT) Retrofit retrofit) {
        return retrofit.create(RedditCommentOauthAPI.class);

    }

}
