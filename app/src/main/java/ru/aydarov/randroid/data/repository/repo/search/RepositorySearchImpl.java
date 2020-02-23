package ru.aydarov.randroid.data.repository.repo.search;

import java.util.List;

import androidx.paging.DataSource;
import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.RedditPostSearch;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchApi;
import ru.aydarov.randroid.data.repository.databases.RedditDaoSearch;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositorySearchImpl implements RepositorySearch {

    private final Lazy<RedditSearchApi> mRedditAPI;
    private final Lazy<RedditDaoSearch> mRedditDao;

    public RepositorySearchImpl(Lazy<RedditSearchApi> redditAPI, Lazy<RedditDaoSearch> redditDao) {
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
    public void insertPost(List<RedditPostSearch> posts) {
        mRedditDao.get().insert(posts);
    }

    @Override
    public Flowable<RedditPostSearch.RedditPostResponse> searchPosts(String query, String sortType, String lastItem, String accessToken, int pageSize) {
        return null;
    }

    @Override
    public Flowable<RedditPostSearch.RedditPostResponse> searchPosts(String query, String sortType, String accessToken, int pageSize) {
        return null;
    }


    @Override
    public void deletePosts() {
        mRedditDao.get().deletePosts();
    }


}
