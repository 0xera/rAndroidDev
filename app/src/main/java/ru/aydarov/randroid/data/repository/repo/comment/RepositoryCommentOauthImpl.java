package ru.aydarov.randroid.data.repository.repo.comment;

import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.CommentList;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentOauthAPI;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryCommentOauthImpl implements RepositoryComment {


    private final Lazy<RedditCommentOauthAPI> mRedditOauthAPI;

    public RepositoryCommentOauthImpl(Lazy<RedditCommentOauthAPI> redditOauthAPI) {
        mRedditOauthAPI = redditOauthAPI;
    }


    @Override
    public Flowable<CommentList> loadPostAndCommentsSingleThreadById(String id, String sortType, String singleCommentId, String accessToken) {
        return mRedditOauthAPI.get().loadPostAndCommentsSingleThreadByIdOauth(id, sortType, singleCommentId, RedditUtilsNet.getOAuthHeader(accessToken));
    }

    @Override
    public Flowable<CommentList> loadPostAndCommentsById(String id, String sortType, String accessToken) {
        return mRedditOauthAPI.get().loadPostAndCommentsByIdOauth(id, sortType, RedditUtilsNet.getOAuthHeader(accessToken));
    }
}
