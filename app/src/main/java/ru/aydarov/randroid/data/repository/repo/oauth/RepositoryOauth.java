package ru.aydarov.randroid.data.repository.repo.oauth;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.aydarov.randroid.data.model.UserData;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryOauth {

    Single<UserData> getUserDataApi(String accessToken);

    Flowable<UserData> getUserDataDao();

    void saveUserData(UserData userData);

    void deleteUser(String username);
}
