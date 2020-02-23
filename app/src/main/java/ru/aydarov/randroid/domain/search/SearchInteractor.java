package ru.aydarov.randroid.domain.search;

import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public interface SearchInteractor {
    ListingPost<RedditPost> getPosts(String sortType, int pageSize, String searchQuery);

    Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String lastItem, int pageSize, String searchQuery);

    void insertResultIntoDb(String sorType, RedditPost.RedditPostResponse response, String searchQuery);

    void dispose();
}
