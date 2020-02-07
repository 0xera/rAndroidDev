package ru.aydarov.randroid.data.util;

/**
 * @author Aydarov Askhar 2020
 */
public class Constants {
    public static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope=identity";

    public static final String CLIENT_ID = "pZWSC6IJWt5jXQ";

    public static final String REDIRECT_URI =
            "http://www.http://www.randroiddev.com/my_redirect";

    public static final String STATE_AUTH = "STATE_FOR_THIS_APP_RANDROIDDEV";

    public static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";

    public static final String SUBREDDIT = "r/{androiddev}";
}
