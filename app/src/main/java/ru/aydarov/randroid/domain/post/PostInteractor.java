package ru.aydarov.randroid.domain.post;

import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public interface PostInteractor {
    ListingPost<RedditPost> getPosts(String sortType, int pageSize);

    Flowable<RedditPost.RedditPostResponse> loadPosts(String sortType, String lastItem, int pageSize);

    void insertResultIntoDb(String sorType, RedditPost.RedditPostResponse response);

    void dispose();
}
