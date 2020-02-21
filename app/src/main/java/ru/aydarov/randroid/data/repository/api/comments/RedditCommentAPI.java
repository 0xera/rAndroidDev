package ru.aydarov.randroid.data.repository.api.comments;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.CommentList;

/**
 * @author Aydarov Askhar 2020
 */
public interface RedditCommentAPI {


    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<CommentList> loadPostAndCommentsSingleThreadById(@Path("id") String id, @Query("sort") String sortType, @Path("singleCommentId") String singleCommentId);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<CommentList> loadPostAndCommentsById(@Path("id") String id, @Query("sort") String sortType);

}
