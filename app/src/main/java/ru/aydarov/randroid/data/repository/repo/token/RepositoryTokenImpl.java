package ru.aydarov.randroid.data.repository.repo.token;

import java.util.Map;

import dagger.Lazy;
import io.reactivex.Single;
import retrofit2.Call;
import ru.aydarov.randroid.data.model.Token;
import ru.aydarov.randroid.data.repository.api.RedditTokenAPI;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryTokenImpl implements RepositoryToken {

    private final Lazy<RedditTokenAPI> mRedditTokenAPI;

    public RepositoryTokenImpl(Lazy<RedditTokenAPI> redditTokenAPI) {
        mRedditTokenAPI = redditTokenAPI;
    }


    @Override
    public Single<Token> getToken(Map<String, String> header, Map<String, String> params) {
        return mRedditTokenAPI.get().getAccessToken(header, params);
    }

    @Override
    public Call<Token> getTokenCall(Map<String, String> header, Map<String, String> params) {
        return mRedditTokenAPI.get().getAccessTokenCall(header, params);
    }
}
