package ru.aydarov.randroid.data.repository.repo.post;

/**
 * @author Aydarov Askhar 2020
 */
public class NetworkState {

    private Status mStatus;

    private String mMsg;

    private NetworkState(String msg) {
        mStatus = Status.FAILED;
        mMsg = msg;
    }

    private NetworkState(Status status) {
        mStatus = status;
    }

    public Status getStatus() {
        return mStatus;
    }

    public String getMsg() {
        return mMsg;
    }

    public static NetworkState LOADED = new NetworkState(Status.SUCCESS);
    public static NetworkState LOADING = new NetworkState(Status.RUNNING);

    public static NetworkState error(String msg) {
        return new NetworkState(msg);
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}


