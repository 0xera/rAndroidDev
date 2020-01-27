package ru.aydarov.randroid.presentation.common;

import android.os.Bundle;

/**
 * @author Aydarov Askhar 2020
 */
public interface INavigator {
    void navigateFromNewsToSearchedFragment(Bundle extras);

    void navigateFromSearchedToSelf(Bundle extras);

    void navigateFromUserToSelf();
}
