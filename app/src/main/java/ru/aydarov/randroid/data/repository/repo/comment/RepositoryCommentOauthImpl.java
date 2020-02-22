package ru.aydarov.randroid.data.repository.repo.comment;

import dagger.Lazy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
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
    public Flowable<String> loadPostAndCommentsSingleThreadById(String id, String sortType, String singleCommentId, String accessToken) {
        return mRedditOauthAPI.get().loadPostAndCommentsSingleThreadByIdOauth(id, sortType, singleCommentId, RedditUtilsNet.getOAuthHeader(accessToken));
    }

    @Override
    public Flowable<String> loadPostAndCommentsById(String id, String sortType, String accessToken) {
        return mRedditOauthAPI.get().loadPostAndCommentsByIdOauth(id, sortType, RedditUtilsNet.getOAuthHeader(accessToken));
    }

    @Override
    public Flowable<ResponseBody> loadPostAndCommentsByIdBody(String id, String sortType, String accessToken) {
        return mRedditOauthAPI.get().loadPostAndCommentsByIdOauthBody(id, sortType, RedditUtilsNet.getOAuthHeader(accessToken));

    }
}
