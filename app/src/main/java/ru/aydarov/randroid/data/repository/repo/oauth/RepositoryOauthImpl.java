package ru.aydarov.randroid.data.repository.repo.oauth;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.aydarov.randroid.data.model.UserData;
import ru.aydarov.randroid.data.repository.api.RedditOauthAPI;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.util.RedditUtils;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryOauthImpl implements RepositoryOauth {

    private final Lazy<RedditOauthAPI> mRedditUserAPI;
    private final Lazy<RedditDao> mRedditUserDao;
    private Disposable mDisposable;

    public RepositoryOauthImpl(Lazy<RedditOauthAPI> redditUserAPI, Lazy<RedditDao> redditUserDao) {
        mRedditUserAPI = redditUserAPI;
        mRedditUserDao = redditUserDao;
    }

    @Override
    public Single<UserData> getUserDataApi(String accessToken) {
        return mRedditUserAPI.get().getMyInfo(RedditUtils.getOAuthHeader(accessToken));
    }

    @Override
    public Flowable<UserData> getUserDataDao() {
        return mRedditUserDao.get().getUserData().distinctUntilChanged();
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
