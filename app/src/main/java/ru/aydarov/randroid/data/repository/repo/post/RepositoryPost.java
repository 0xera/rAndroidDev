package ru.aydarov.randroid.data.repository.repo.post;

import java.util.List;

import androidx.paging.DataSource;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.RedditPostResponse;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryPost {
    DataSource.Factory<Integer, RedditPost> getPostsDb();

    DataSource.Factory<Integer, RedditPost> getSearchedPostsDb(String searchQuery);

    int getNextIndexInSubreddit();

    void insertPost(List<RedditPost> posts);

    Flowable<RedditPostResponse> loadPosts(String sortType, String lastItem, String accessToken, int pageSize);

    Flowable<RedditPostResponse> loadPosts(String sortType, String accessToken, int pageSize);

    Flowable<RedditPostResponse> searchPosts(String query, String sortType, String lastItem, String accessToken, int pageSize);

    Flowable<RedditPostResponse> searchPosts(String query, String sortType, String accessToken, int pageSize);

    void deletePosts();

    int getNextIndexInSubreddit(String searchQuery);

}
