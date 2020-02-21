package ru.aydarov.randroid.data.util;

import android.net.Uri;
import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Aydarov Askhar 2020
 */
public class RedditUtilsNet {

    public static final String NAME_KEY = "name";
    public static final String ICON_IMG_KEY = "icon_img";
    public static final String BANNER_IMG_KEY = "banner_img";
    public static final String DESCRIPTION_KEY = "public_description";
    public static final String SUBREDDIT_KEY = "subreddit";

    public static final String OAUTH_URL = "https://www.reddit.com/api/v1/authorize.compact";
    public static final String OAUTH_API_BASE_URI = "https://oauth.reddit.com";

    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_ID = "pZWSC6IJWt5jXQ";
    public static final String RESPONSE_TYPE_KEY = "response_type";
    public static final String API_BASE_URI = "https://www.reddit.com";
    public static final String RESPONSE_TYPE = "code";
    public static final String STATE_KEY = "state";
    public static final String STATE = "STATE_FOR_THIS_APP_RANDROIDDEV";
    public static final String REDIRECT_URI_KEY = "redirect_uri";
    public static final String REDIRECT_URI = "http://www.randroiddev.com/my_redirect";
    public static final String DURATION_KEY = "duration";
    public static final String DURATION = "permanent";
    public static final String SCOPE_KEY = "scope";
    public static final String SCOPE = "identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread";
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String EXPIRES_KEY = "expires_in";
    public static final String VIDEO = "VIDEO";
    public static final String IMAGE = "IMAGE";
    public static final String TEXT = "TEXT";
    public static final String LINK = "LINK";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String AUTHORIZATION_BASE = "bearer ";
    public static final String USER_AGENT_KEY = "User-Agent";
    public static final String USER_AGENT = "rAndroid";
    public static final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String HOT = "hot";
    public static final String NEW = "new";
    public static final String TOP = "top";
    public static final String CONTROVERSIAL = "controversial";
    public static final String PAYLOAD_DIFF = "payload_diff";


    public static Map<String, String> getHttpBasicAuthHeader() {
        Map<String, String> params = new HashMap<>();
        String credentials = String.format("%s:", RedditUtilsNet.CLIENT_ID);
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        params.put(RedditUtilsNet.AUTHORIZATION_KEY, auth);
        return params;
    }

    public static Map<String, String> getOAuthHeader(String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put(RedditUtilsNet.USER_AGENT_KEY, RedditUtilsNet.USER_AGENT);
        params.put(RedditUtilsNet.AUTHORIZATION_KEY, RedditUtilsNet.AUTHORIZATION_BASE + accessToken);
        return params;
    }

    public static RequestBody getRequestBody(String s) {
        return RequestBody.create(s, MediaType.parse("text/plain"));
    }

    public static Map<String, String> getParamsAuth(Uri uri) {
        String authCode = uri.getQueryParameter(RedditUtilsNet.RESPONSE_TYPE);
        Map<String, String> params = new HashMap<>();
        params.put(RedditUtilsNet.GRANT_TYPE_KEY, RedditUtilsNet.AUTHORIZATION_CODE);
        params.put(RedditUtilsNet.RESPONSE_TYPE, authCode);
        params.put(RedditUtilsNet.REDIRECT_URI_KEY, RedditUtilsNet.REDIRECT_URI);
        return params;
    }

    public static Map<String, String> getParamsRefresh(String refreshToken) {
        Map<String, String> params = new HashMap<>();
        params.put(RedditUtilsNet.GRANT_TYPE_KEY, RedditUtilsNet.GRANT_TYPE_REFRESH_TOKEN);
        params.put(RedditUtilsNet.REFRESH_TOKEN_KEY, refreshToken);
        return params;
    }
}