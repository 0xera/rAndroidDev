package ru.aydarov.randroid.domain.user;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.model.UserData;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryOauth;

/**
 * @author Aydarov Askhar 2020
 */
public class UserInteractorImpl implements UserInteractor {


    private final Lazy<RepositoryOauth> mRepository;
    private Disposable mDisposable;

    public UserInteractorImpl(Lazy<RepositoryOauth> repository) {
        mRepository = repository;

    }

    @Override
    public Flowable<UserData> getUserData(String accessToken) {
        mDisposable = mRepository.get().getUserDataApi(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(userData -> mRepository.get().saveUserData(userData), throwable -> { });
        return mRepository.get().getUserDataDao().distinctUntilChanged();
    }

    @Override
    public void logOut(String username) {
        mRepository.get().deleteUser(username);
    }

    @Override
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
