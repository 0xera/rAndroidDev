package ru.aydarov.randroid.data.repository.repo.comment;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * @author Aydarov Askhar 2020
 */
public interface RepositoryComment {
    Flowable<String> loadPostAndCommentsSingleThreadById(String id, String sortType, String singleCommentId, String accessToken);

    Flowable<String> loadPostAndCommentsById(String id, String sortType, String accessToken);

    Flowable<ResponseBody> loadPostAndCommentsByIdBody(String id, String sortType, String accessToken);

}
