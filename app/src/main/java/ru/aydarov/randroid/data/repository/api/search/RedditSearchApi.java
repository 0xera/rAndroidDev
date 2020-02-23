package ru.aydarov.randroid.data.repository.api.search;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.RedditPostResponse;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

public interface RedditSearchApi {
    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPosts(
            @Query("q") String query, @Query("sort") String sort,
            @Query("after") String after, @Query("limit") int limit);

    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<RedditPostResponse> searchPosts(
            @Query("q") String query, @Query("sort") String sort, @Query("limit") int limit);

}
