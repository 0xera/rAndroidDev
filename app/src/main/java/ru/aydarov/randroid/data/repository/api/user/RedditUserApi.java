package ru.aydarov.randroid.data.repository.api.user;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import ru.aydarov.randroid.data.model.UserData;

public interface RedditUserApi {
    @GET("api/v1/me?raw_json=1")
    Single<UserData> getMyInfo(@HeaderMap Map<String, String> headers);
}
