package ru.aydarov.randroid.data.repository.api.post;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.RedditPostResponse;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */
public interface RedditPostAPI {
    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPostResponse> loadPosts(@Path("sortType") String sortType,
                                           @Query("after") String lastItem, @Query("limit") int limit);

    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPostResponse> loadPosts(@Path("sortType") String sortType,
                                           @Query("limit") int limit);


    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPosts(
            @Query("q") String query, @Query("sort") String sort,
            @Query("after") String after, @Query("limit") int limit);

    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPosts(
            @Query("q") String query, @Query("sort") String sort, @Query("limit") int limit);


}
