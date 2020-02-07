package ru.aydarov.randroid.data.repository.api;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.Token;

/**
 * @author Aydarov Askhar 2020
 */

public interface RedditTokenAPI {
    @FormUrlEncoded
    @POST("api/v1/access_token")
    Single<Token> getAccessToken(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/v1/access_token")
    Call<Token> getAccessTokenCall(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<String> getPostAndCommentsSingleThreadById(@Path("id") String id, @Path("singleCommentId") String singleCommentId,
                                                        @Query("sort") String sortType);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<String> getPostAndCommentsById(@Path("id") String id, @Query("sort") String sortType);

}
