package ru.aydarov.randroid.data.repository.repo.post;

import dagger.Lazy;
import ru.aydarov.randroid.data.repository.api.RedditAPI;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryPostImpl implements RepositoryPost {

    private final Lazy<RedditAPI> mRedditAPI;

    public RepositoryPostImpl(Lazy<RedditAPI> redditAPI) {
        mRedditAPI = redditAPI;
    }
}
