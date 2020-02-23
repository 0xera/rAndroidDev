package ru.aydarov.randroid.data.repository.repo.post;

import java.util.List;

import androidx.paging.DataSource;
import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.api.post.RedditPostAPI;
import ru.aydarov.randroid.data.repository.databases.RedditDao;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryPostImpl implements RepositoryPost {

    private final Lazy<RedditPostAPI> mRedditAPI;
    private final Lazy<RedditDao> mRedditDao;

    public RepositoryPostImpl(Lazy<RedditPostAPI> redditAPI, Lazy<RedditDao> redditDao) {
        mRedditAPI = redditAPI;
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
        return mRedditAPI.get().loadPosts(sortType, lastItem, pageSize);
    }

    @Override
    public Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String accessToken, int pageSize) {
        return mRedditAPI.get().loadPosts(sortType, pageSize);
    }


    @Override
    public void deletePosts() {
        mRedditDao.get().deletePosts();
    }


}
