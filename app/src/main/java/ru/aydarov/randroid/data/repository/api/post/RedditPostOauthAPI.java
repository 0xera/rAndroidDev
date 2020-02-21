package ru.aydarov.randroid.data.repository.api.post;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.RedditPostResponse;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */

public interface RedditPostOauthAPI {



    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPostResponse> loadPostsOauth(@Path("sortType") String sortType, @Query("after") String lastItem,
                                                @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);

    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1")
    Flowable<RedditPostResponse> loadPostsOauth(@Path("sortType") String sortType,
                                                @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);


    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPostsOauth(
            @Query("q") String query, @Query("sort") String sort, @Query("after") String after,
            @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);

    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPostsOauth(
            @Query("q") String query, @Query("sort") String sort,
            @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);




}
