package ru.aydarov.randroid.data.repository.api.comments;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Aydarov Askhar 2020
 */
public interface RedditCommentAPI {


    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<String> loadPostAndCommentsSingleThreadById(@Path("id") String id, @Query("sort") String sortType, @Path("singleCommentId") String singleCommentId);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<String> loadPostAndCommentsById(@Path("id") String id, @Query("sort") String sortType);

}
