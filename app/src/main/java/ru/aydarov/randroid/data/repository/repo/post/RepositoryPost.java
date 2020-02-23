package ru.aydarov.randroid.data.repository.repo.post;

import java.util.List;

import androidx.paging.DataSource;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;


/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryPost {
    DataSource.Factory<Integer, RedditPost> getPostsDb();

    int getNextIndexInSubreddit();

    void insertPost(List<RedditPost> posts);

    Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String lastItem, String accessToken, int pageSize);

    Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String accessToken, int pageSize);

    void deletePosts();

}
