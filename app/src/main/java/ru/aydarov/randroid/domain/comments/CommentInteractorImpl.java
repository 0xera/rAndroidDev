package ru.aydarov.randroid.domain.comments;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.MutableLiveData;
import dagger.Lazy;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.repository.repo.comment.RepositoryComment;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.util.TokensSharedHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class CommentInteractorImpl implements CommentInteractor {

    private final Lazy<SharedPreferences> mSharedPreferences;
    private Lazy<RepositoryComment> mRepositoryComment;
    private Lazy<RepositoryComment> mRepositoryCommentOauth;
    private Disposable mDisposable;
    private MutableLiveData<NetworkCommentResult> mLiveDataComment = new MutableLiveData<>(NetworkCommentResult.NONE);
    private String TAG = "CommentInteractor";
    private MutableLiveData<NetworkCommentResult> mLiveDataCommentSingle = new MutableLiveData<>(NetworkCommentResult.NONE);


    public CommentInteractorImpl(Lazy<RepositoryComment> repositoryComment, Lazy<RepositoryComment> repositoryCommentOauth, Lazy<SharedPreferences> sharedPreferences) {
        mRepositoryComment = repositoryComment;
        mRepositoryCommentOauth = repositoryCommentOauth;
        mSharedPreferences = sharedPreferences;
    }


    @Override
    public MutableLiveData<NetworkCommentResult> getComments(String id, String sortType) {
        mLiveDataComment.setValue(NetworkCommentResult.LOADING);
        mDisposable = getCurrentRepo()
                .loadPostAndCommentsById(id, sortType, getToken(RedditUtilsNet.ACCESS_TOKEN_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {
                            Log.d(TAG, "getComments() called with: id = [" + id + "], sortType = [" + sortType + "]");
//                            if (comments != null && comments.size() > 0 && comments.get(0) != null && comments.get(0).getData() != null) {
//                                Data data = comments.get(0).getData();
//                                if (data != null && data.getComments() != null)
//                                    mLiveDataComment.postValue(NetworkCommentResult.success(data.getComments()));
////                            }
//                            if (comments != null && comments.size() > 0) {
//                                mLiveDataComment.postValue(NetworkCommentResult.success(comments));
                            parseResponse(mLiveDataComment, response);
                        },
                        throwable -> {
                            Log.d(TAG, "accept() called with: throwable = [" + throwable + "]");
                            mLiveDataComment.postValue(NetworkCommentResult.error(throwable.getMessage()));
                        });

        return mLiveDataComment;
    }

    private void parseResponse(MutableLiveData<NetworkCommentResult> liveDataComment, String response) throws JSONException {
        JSONObject data = new JSONObject(response).getJSONObject(JSONUtils.DATA_KEY);
        JSONArray jsonArray = data.getJSONArray(JSONUtils.CHILDREN_KEY);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject commentJSON = jsonArray.getJSONObject(i).getJSONObject(JSONUtils.DATA_KEY);
                comments.add(ParseComment.parseSingleComment(commentJSON, 0, locale));
            } catch (JSONException ignored) {
            }
        }
    }

    @Override
    public MutableLiveData<NetworkCommentResult> getCommentsBySingleThread(String id, String sortType, String singleCommentId) {
        mLiveDataCommentSingle.setValue(NetworkCommentResult.LOADING);
        mDisposable = getCurrentRepo()
                .loadPostAndCommentsSingleThreadById(id, sortType, singleCommentId, getToken(RedditUtilsNet.ACCESS_TOKEN_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(comments -> {
                            Log.d(TAG, "getComments() called with: id = [" + id + "], sortType = [" + sortType + "]");
//                            if (comments != null && comments.size() > 0 && comments.get(0) != null && comments.get(0).getData() != null) {
//                                Data data = comments.get(0).getData();
//                                if (data != null && data.getComments() != null)
//                                    mLiveDataCommentSingle.postValue(NetworkCommentResult.success(data.getComments()));
                            if (comments != null && comments.size() > 0) {
                                mLiveDataCommentSingle.postValue(NetworkCommentResult.success(comments));
                            }
                        },
                        throwable -> {
                            Log.d(TAG, "accept() called with: throwable = [" + throwable + "]");
                            mLiveDataCommentSingle.postValue(NetworkCommentResult.error(throwable.getMessage()));
                        });
        return mLiveDataCommentSingle;
    }

    @Override
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    private boolean isEmptyTokens() {
        return TextUtils.isEmpty(getToken(RedditUtilsNet.ACCESS_TOKEN_KEY)) || TextUtils.isEmpty(getToken(RedditUtilsNet.REFRESH_TOKEN_KEY));
    }


    @NotNull
    private String getToken(String accessTokenKey) {
        return mSharedPreferences.get().getString(accessTokenKey, TokensSharedHelper.NONE);
    }

    private RepositoryComment getCurrentRepo() {
        if (isEmptyTokens())
            return mRepositoryComment.get();
        else return mRepositoryCommentOauth.get();
    }


}
