package ru.aydarov.randroid.domain.user;

import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.UserData;

/**
 * @author Aydarov Askhar 2020
 */
public interface UserInteractor {
    Flowable<UserData> getUserData(String accessToken);

    void logOut(String username);

    void dispose();
}
