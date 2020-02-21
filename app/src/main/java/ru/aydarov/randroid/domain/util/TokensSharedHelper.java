package ru.aydarov.randroid.domain.util;

import android.content.Context;

import ru.aydarov.randroid.data.util.RedditUtilsNet;

/**
 * @author Aydarov Askhar 2020
 */
public class TokensSharedHelper {

    public static final String TOKENS_PREF = "tokens";
    public static final String NONE = "";

    public static void saveAccessToken(Context context, String token) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putString(RedditUtilsNet.ACCESS_TOKEN_KEY, token).apply();
    }

    public static void saveRefreshToken(Context context, String token) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putString(RedditUtilsNet.REFRESH_TOKEN_KEY, token).apply();
    }


    public static void saveExpiresIn(Context context, long expiresIn) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putLong(RedditUtilsNet.EXPIRES_KEY, expiresIn).apply();
    }

    public static long getExpiresIn(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getLong(RedditUtilsNet.EXPIRES_KEY, 0);
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getString(RedditUtilsNet.ACCESS_TOKEN_KEY, NONE);
    }

    public static String getRefreshToken(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getString(RedditUtilsNet.REFRESH_TOKEN_KEY, NONE);

    }

}
