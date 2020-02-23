package ru.aydarov.randroid.data.repository.api.search;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.RedditPost;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */

public interface RedditSerachOauthAPI {





    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPost.RedditPostResponse> searchPostsOauth(
            @Query("q") String query, @Query("sort") String sort, @Query("after") String after,
            @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);

    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPost.RedditPostResponse> searchPostsOauth(
            @Query("q") String query, @Query("sort") String sort,
            @HeaderMap Map<String, String> headers, @Query("limit") int pageSize);




}
