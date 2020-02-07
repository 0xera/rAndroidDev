package ru.aydarov.randroid.data.util;

import android.content.Context;

/**
 * @author Aydarov Askhar 2020
 */
public class SortTypeHelper {
    private static String PREF_NAME = "pref_sort";
    private static String SORT_KEY = "SORT_KEY";


    public static void setSortType(Context context, int sortType) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putInt(SORT_KEY, sortType).apply();
    }

    public static int getSortType(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(SORT_KEY, 0);
    }
}
