package ru.aydarov.randroid.domain.comments;

import androidx.lifecycle.MutableLiveData;

/**
 * @author Aydarov Askhar 2020
 */
public interface CommentInteractor {
    MutableLiveData<NetworkCommentResult> getComments(String id, String sortType);

    MutableLiveData<NetworkCommentResult> getCommentsBySingleThread(String id, String sortType, String singleCommentId);

    void dispose();
}
