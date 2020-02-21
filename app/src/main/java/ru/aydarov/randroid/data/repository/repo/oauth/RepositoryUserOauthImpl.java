package ru.aydarov.randroid.data.repository.repo.oauth;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.aydarov.randroid.data.model.UserData;
import ru.aydarov.randroid.data.repository.api.user.RedditUserApi;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryUserOauthImpl implements RepositoryUserOauth {

    private final Lazy<RedditUserApi> mRedditUserAPI;
    private final Lazy<RedditDao> mRedditUserDao;
    private Disposable mDisposable;

    public RepositoryUserOauthImpl(Lazy<RedditUserApi> redditUserAPI, Lazy<RedditDao> redditUserDao) {
        mRedditUserAPI = redditUserAPI;
        mRedditUserDao = redditUserDao;
    }

    @Override
    public Single<UserData> getUserDataApi(String accessToken) {
        return mRedditUserAPI.get().getMyInfo(RedditUtilsNet.getOAuthHeader(accessToken));
    }

    @Override
    public Flowable<UserData> getUserDataDao() {
        return mRedditUserDao.get().getUserData();
    }

    @Override
    public void saveUserData(UserData userData) {
        mRedditUserDao.get().insertUser(userData);
    }

    @Override
    public void deleteUser(String username) {
        mRedditUserDao.get().deleteUser(username);
    }


}
