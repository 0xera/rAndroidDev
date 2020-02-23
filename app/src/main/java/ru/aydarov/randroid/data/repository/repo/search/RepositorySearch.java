package ru.aydarov.randroid.data.repository.repo.search;

import java.util.List;

import androidx.paging.DataSource;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.RedditPostSearch;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositorySearch {

    DataSource.Factory<Integer, RedditPost> getSearchedPostsDb(String searchQuery);


    void insertPost(List<RedditPostSearch> posts);


    Flowable<RedditPostSearch.RedditPostResponse> searchPosts(String query, String sortType, String lastItem, String accessToken, int pageSize);

    Flowable<RedditPostSearch.RedditPostResponse> searchPosts(String query, String sortType, String accessToken, int pageSize);

    void deletePosts();

    int getNextIndexInSubreddit(String searchQuery);

}
