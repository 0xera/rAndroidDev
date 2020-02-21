package ru.aydarov.randroid.data.repository.api.comments;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.CommentList;

/**
 * @author Aydarov Askhar 2020
 */

public interface RedditCommentOauthAPI {


//    @FormUrlEncoded
//    @POST("api/comment")
//    Flowable<String> sendComment(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<CommentList> loadPostAndCommentsSingleThreadByIdOauth(@Path("id") String id, @Query("sort") String sortType, @Path("singleCommentId") String singleCommentId,
                                                                   @HeaderMap Map<String, String> headers);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<CommentList> loadPostAndCommentsByIdOauth(@Path("id") String id, @Query("sort") String sortType,
                                                       @HeaderMap Map<String, String> headers);


}
