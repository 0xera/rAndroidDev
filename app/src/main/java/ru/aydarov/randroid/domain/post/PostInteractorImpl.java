package ru.aydarov.randroid.domain.post;

import android.content.SharedPreferences;

import dagger.Lazy;
import ru.aydarov.randroid.data.repository.repo.oauth.RepositoryOauth;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPost;

/**
 * @author Aydarov Askhar 2020
 */
public class PostInteractorImpl implements PostInteractor {

    private final Lazy<RepositoryOauth> mRepositoryOauth;
    private final Lazy<RepositoryPost> mRepositoryPost;
    private final Lazy<SharedPreferences> mSharedPreferences;

    public PostInteractorImpl(Lazy<RepositoryOauth> repositoryOauth, Lazy<RepositoryPost> repositoryPost, Lazy<SharedPreferences> sharedPreferences) {
        mRepositoryOauth = repositoryOauth;
        mRepositoryPost = repositoryPost;
        mSharedPreferences = sharedPreferences;
    }
}
