package ru.aydarov.randroid.presentation.common;

import android.content.Intent;
import android.view.View;

/**
 * @author Aydarov Askhar 2020
 */
public interface INavigatorSource {
    void navigateToSourceViewActivity(View view, Intent intent);
    void onShare(Intent intent);
}
