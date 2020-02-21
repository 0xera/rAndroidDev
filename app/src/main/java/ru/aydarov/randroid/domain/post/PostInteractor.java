package ru.aydarov.randroid.domain.post;

import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.RedditPostResponse;

/**
 * @author Aydarov Askhar 2020
 */
public interface PostInteractor {
    ListingPost<RedditPost> getPosts(String sortType, int pageSize, String searchQuery);

    Flowable<RedditPostResponse> loadPosts(String sortType, String lastItem, int pageSize, String searchQuery);

    void insertResultIntoDb(String sorType, RedditPostResponse response, String searchQuery);

    void dispose();
}
