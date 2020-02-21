package ru.aydarov.randroid.data.repository.repo.comment;

import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.CommentList;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryComment {
    Flowable<CommentList> loadPostAndCommentsSingleThreadById(String id, String sortType, String singleCommentId, String accessToken);

    Flowable<CommentList> loadPostAndCommentsById(String id, String sortType, String accessToken);

}
