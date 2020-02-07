package ru.aydarov.randroid.data.repository.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */
public interface RedditAPI {
    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1&limit=25")
    Flowable<String> getSubredditBestPosts(@Path("sortType") String sortType,
                                           @Query("after") String lastItem);


    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<String> searchPostsInSpecificSubreddit(
            @Query("q") String query, @Query("sort") String sort,
            @Query("after") String after);

    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<String> getPostAndCommentsSingleThreadById(@Path("id") String id, @Path("singleCommentId") String singleCommentId,
                                                        @Query("sort") String sortType);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<String> getPostAndCommentsById(@Path("id") String id, @Query("sort") String sortType);

}
