package ru.aydarov.randroid.data.repository.repo.post;

import java.util.List;

import androidx.paging.DataSource;
import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.api.post.RedditPostOauthAPI;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryPostOauthImpl implements RepositoryPost {


    private final Lazy<RedditPostOauthAPI> mRedditOauthAPI;
    private final Lazy<RedditDao> mRedditDao;

    public RepositoryPostOauthImpl(Lazy<RedditPostOauthAPI> redditOauthAPI, Lazy<RedditDao> redditDao) {
        mRedditOauthAPI = redditOauthAPI;
        mRedditDao = redditDao;
    }

    @Override
    public DataSource.Factory<Integer, RedditPost> getPostsDb() {
        return mRedditDao.get().getPosts();

    }



    @Override
    public int getNextIndexInSubreddit() {
        return mRedditDao.get().getNextIndexInSubreddit();
    }

    @Override
    public void insertPost(List<RedditPost> posts) {
        mRedditDao.get().insert(posts);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String lastItem, String accessToken, int pageSize) {
        return mRedditOauthAPI.get().loadPostsOauth(sortType, lastItem, RedditUtilsNet.getOAuthHeader(accessToken), pageSize);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String accessToken, int pageSize) {
        return mRedditOauthAPI.get().loadPostsOauth(sortType, RedditUtilsNet.getOAuthHeader(accessToken), pageSize);
    }


    @Override
    public void deletePosts() {
        mRedditDao.get().deletePosts();
    }


}
