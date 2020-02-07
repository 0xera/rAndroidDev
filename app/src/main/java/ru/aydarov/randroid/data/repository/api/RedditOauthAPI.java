package ru.aydarov.randroid.data.repository.api;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.aydarov.randroid.data.model.UserData;

import static ru.aydarov.randroid.data.util.Constants.SUBREDDIT;

/**
 * @author Aydarov Askhar 2020
 */

public interface RedditOauthAPI {


    @GET("api/v1/me?raw_json = 1")
    Single<UserData> getMyInfo(@HeaderMap Map<String, String> headers);

    @GET(SUBREDDIT + "/{sortType}.json?raw_json=1&limit=25")
    Flowable<String> getSubredditBestPostsOauth(@Path("sortType") String sortType, @Query("after") String lastItem,
                                                @HeaderMap Map<String, String> headers);


    @GET(SUBREDDIT + "/search.json?include_over_18=1&raw_json=1&type=link&restrict_sr=true")
    Flowable<String> searchPostsInSpecificSubredditOauth(
            @Query("q") String query, @Query("sort") String sort, @Query("after") String after,
            @HeaderMap Map<String, String> headers);


    @FormUrlEncoded
    @POST("api/comment")
    Flowable<String> sendComment(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @GET("/comments/{id}/placeholder/{singleCommentId}.json?context=8&raw_json=1")
    Flowable<String> getPostAndCommentsSingleThreadByIdOauth(@Path("id") String id, @Path("singleCommentId") String singleCommentId,
                                                             @Query("sort") String sortType,
                                                             @HeaderMap Map<String, String> headers);

    @GET("/comments/{id}.json?raw_json=1")
    Flowable<String> getPostAndCommentsByIdOauth(@Path("id") String id, @Query("sort") String sortType,
                                                 @HeaderMap Map<String, String> headers);


}
