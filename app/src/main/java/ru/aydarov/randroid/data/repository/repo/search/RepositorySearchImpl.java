package ru.aydarov.randroid.data.repository.repo.search;

import java.util.List;

import androidx.paging.DataSource;
import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchApi;
import ru.aydarov.randroid.data.repository.databases.RedditSearchDao;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositorySearchImpl implements RepositorySearch {

    private final Lazy<RedditSearchApi> mRedditAPI;
    private final Lazy<RedditSearchDao> mRedditDao;

    public RepositorySearchImpl(Lazy<RedditSearchApi> redditAPI, Lazy<RedditSearchDao> redditDao) {
        mRedditAPI = redditAPI;
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
        return mRedditAPI.get().searchPosts(query, sortType, lastItem, pageSize);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> searchPosts(String query, String sortType, String accessToken, int pageSize) {
        return mRedditAPI.get().searchPosts(query, sortType, pageSize);
    }


    @Override
    public void deletePosts() {
        mRedditDao.get().deletePosts();
    }


}
