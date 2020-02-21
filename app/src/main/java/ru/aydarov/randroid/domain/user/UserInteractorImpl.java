package ru.aydarov.randroid.domain.user;

import android.util.Log;

import dagger.Lazy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.model.UserData;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryUserOauth;

/**
 * @author Aydarov Askhar 2020
 */
public class UserInteractorImpl implements UserInteractor {


    private static final String TAG = "thidisodsi";
    private final Lazy<RepositoryUserOauth> mRepository;
    private Disposable mDisposable;

    public UserInteractorImpl(Lazy<RepositoryUserOauth> repository) {
        mRepository = repository;

    }

    @Override
    public Flowable<UserData> getUserData(String accessToken) {
        mDisposable = mRepository.get().getUserDataApi(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(userData -> mRepository.get().saveUserData(userData), throwable -> {
                    Log.d(TAG, "getUserData() called with: accessToken = [" + accessToken + "]");
                });
        return mRepository.get().getUserDataDao().distinctUntilChanged();
    }

    @Override
    public void logOut(String username) {
        Completable.fromAction(() -> mRepository.get().deleteUser(username))
                .subscribeOn(Schedulers.io())
                .subscribe();

    }

    @Override
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
