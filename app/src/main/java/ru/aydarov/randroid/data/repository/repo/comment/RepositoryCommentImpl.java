package ru.aydarov.randroid.data.repository.repo.comment;

import dagger.Lazy;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.CommentList;
import ru.aydarov.randroid.data.repository.api.comments.RedditCommentAPI;

/**
 * @author Aydarov Askhar 2020
 */
public class RepositoryCommentImpl implements RepositoryComment {

    private final Lazy<RedditCommentAPI> mRedditAPI;

    public RepositoryCommentImpl(Lazy<RedditCommentAPI> redditAPI) {
        mRedditAPI = redditAPI;
    }


    @Override
    public Flowable<CommentList> loadPostAndCommentsSingleThreadById(String id, String sortType, String singleCommentId, String accessToken) {
//
//        new ListingPost<>(pagedListLiveData, mRedditBoundaryCallback.get().getNetworkState(), refreshState, () -> {
//            refreshTrigger.setValue(null);
//            return null;
//        }, () -> {
//            mRedditBoundaryCallback.get().getPagingRequestHelper().retryAllFailed();
//            return null;
//        });
//        return mRedditAPI.get().loadPostAndCommentsSingleThreadById(id, sortType, singleCommentId);
        return mRedditAPI.get().loadPostAndCommentsSingleThreadById(id, sortType, singleCommentId);

    }

    @Override
    public Flowable<CommentList> loadPostAndCommentsById(String id, String sortType, String accessToken) {
        return mRedditAPI.get().loadPostAndCommentsById(id, sortType);
    }
}
