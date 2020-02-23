package ru.aydarov.randroid.presentation.common;

import android.os.Bundle;

/**
 * @author Aydarov Askhar 2020
 */
public interface INavigatorFragment {
    void navigateFromNewsToSearchedFragment(Bundle extras);


    void navigateFromUserToSelf();

    void navigateToNews();

    void navigateToUser();


}
