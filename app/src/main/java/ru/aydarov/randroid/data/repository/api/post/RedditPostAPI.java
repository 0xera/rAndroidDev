package ru.aydarov.randroid.data.repository.api.post;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.RedditPost;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */
public interface RedditPostAPI {
    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPost.RedditPostResponse> loadPosts(@Path("sortType") String sortType,
                                                      @Query("after") String lastItem, @Query("limit") int limit);

    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPost.RedditPostResponse> loadPosts(@Path("sortType") String sortType,
                                                      @Query("limit") int limit);
}
