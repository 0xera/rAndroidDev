package ru.aydarov.randroid.data.util;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;

/**
 * @author Aydarov Askhar 2020
 */
public class ImageUtil {
    @NotNull
    public static Uri getUri(String url) {
        String[] split = url.split("\\?");

        return Uri.parse(split[0]);
    }
}
