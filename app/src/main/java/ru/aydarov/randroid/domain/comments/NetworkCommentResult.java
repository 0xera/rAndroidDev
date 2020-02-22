package ru.aydarov.randroid.domain.comments;

import java.util.List;

import ru.aydarov.randroid.data.model.CommentList;

public class NetworkCommentResult {

    private Status mStatus;
    private String mMsg;
    private List<CommentList> mCommentList;

    private NetworkCommentResult(Status status, String msg) {
        mStatus = status;
        mMsg = msg;
    }

    private NetworkCommentResult(Status status) {
        mStatus = status;
    }

    public NetworkCommentResult(Status status, List<CommentList> commentList) {
        mStatus = status;
        mCommentList = commentList;
    }

    public Status getStatus() {
        return mStatus;
    }

    public List<CommentList> getCommentList() {
        return mCommentList;
    }

    public String getMsg() {
        return mMsg;
    }

    public static NetworkCommentResult NONE = new NetworkCommentResult(Status.NONE);
    public static NetworkCommentResult LOADING = new NetworkCommentResult(Status.LOADING);

    public static NetworkCommentResult error(String msg) {
        return new NetworkCommentResult(Status.FAILED, msg);
    }

    public static NetworkCommentResult success(List<CommentList> commentList) {
        return new NetworkCommentResult(Status.SUCCESS, commentList);
    }

    public enum Status {
        NONE,
        SUCCESS,
        LOADING,
        FAILED
    }
}