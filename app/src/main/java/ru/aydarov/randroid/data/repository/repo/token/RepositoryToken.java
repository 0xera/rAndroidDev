package ru.aydarov.randroid.data.repository.repo.token;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import ru.aydarov.randroid.data.model.Token;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryToken {

    Single<Token> getToken(Map<String, String> httpBasicAuthHeader, Map<String, String> params);

    Call<Token> getTokenCall(Map<String, String> httpBasicAuthHeader, Map<String, String> params);
}
