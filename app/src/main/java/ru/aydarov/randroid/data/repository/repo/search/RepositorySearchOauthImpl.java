package ru.aydarov.randroid.data.repository.repo.search;

import java.util.List;

import androidx.paging.DataSource;
import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.api.search.RedditSerachOauthAPI;
import ru.aydarov.randroid.data.repository.databases.RedditSearchDao;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositorySearchOauthImpl implements RepositorySearch {


    private final Lazy<RedditSerachOauthAPI> mRedditOauthAPI;
    private final Lazy<RedditSearchDao> mRedditDao;

    public RepositorySearchOauthImpl(Lazy<RedditSerachOauthAPI> redditOauthAPI, Lazy<RedditSearchDao> redditDao) {
        mRedditOauthAPI = redditOauthAPI;
        mRedditDao = redditDao;
    }

    @Override
    public DataSource.Factory<Integer, RedditPost> getSearchedPostsDb(String searchQuery) {
        return mRedditDao.get().getPosts(searchQuery);
    }

    @Override
    public int getNextIndexInSubreddit(String searchQuery) {
        return mRedditDao.get().getNextIndexInSubreddit(searchQuery);
    }

    @Override
    public void insertPost(List<RedditPost> posts) {
        mRedditDao.get().insert(posts);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> searchPosts(String query, String sortType, String lastItem, String accessToken, int pageSize) {
        return mRedditOauthAPI.get().searchPostsOauth(query, sortType, lastItem, RedditUtilsNet.getOAuthHeader(accessToken), pageSize);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> searchPosts(String query, String sortType, String accessToken, int pageSize) {
        return mRedditOauthAPI.get().searchPostsOauth(query, sortType, RedditUtilsNet.getOAuthHeader(accessToken), pageSize);
    }

    @Override
    public void deletePosts() {
        mRedditDao.get().deletePosts();
    }


}
