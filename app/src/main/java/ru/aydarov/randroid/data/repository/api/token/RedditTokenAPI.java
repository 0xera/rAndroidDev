package ru.aydarov.randroid.data.repository.api.token;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
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


}
