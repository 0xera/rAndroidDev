package ru.aydarov.randroid.domain.user;

import okhttp3.Headers;
import okhttp3.Request;
import ru.aydarov.randroid.data.util.RedditUtilsNet;

public class HeaderGenerator {

    public Request getHeaders(Request.Builder newBuilder, String newAccessToken) {
        return newBuilder.headers(Headers.of(RedditUtilsNet.getOAuthHeader(newAccessToken))).build();
    }
}
