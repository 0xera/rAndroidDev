package ru.aydarov.randroid.data.util;

import android.content.Context;

/**
 * @author Aydarov Askhar 2020
 */
public class TokensSharedHelper {

    public static final String TOKENS_PREF = "tokens";
    public static final String NONE = "";

    public static void saveAccessToken(Context context, String token) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putString(RedditUtils.ACCESS_TOKEN_KEY, token).apply();
    }

    public static void saveRefreshToken(Context context, String token) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putString(RedditUtils.REFRESH_TOKEN_KEY, token).apply();
    }


    public static void saveExpiresIn(Context context, long expiresIn) {
        context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).edit().putLong(RedditUtils.EXPIRES_KEY, expiresIn).apply();
    }

    public static long getExpiresIn(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getLong(RedditUtils.EXPIRES_KEY, 0);
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getString(RedditUtils.ACCESS_TOKEN_KEY, NONE);
    }

    public static String getRefreshToken(Context context) {
        return context.getSharedPreferences(TOKENS_PREF, Context.MODE_PRIVATE).getString(RedditUtils.REFRESH_TOKEN_KEY, NONE);

    }

}
