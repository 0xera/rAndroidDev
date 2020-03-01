package ru.aydarov.randroid.domain.token;

import androidx.lifecycle.MutableLiveData;
import ru.aydarov.randroid.presentation.ui.web.WebViewViewModel;

/**
 * @author Aydarov Askhar 2020
 */
public interface TokenInteractor {

    boolean checkUrlAndGetTokens(MutableLiveData<WebViewViewModel.State> state, String url);

    String getUrl();

    void dispose();

    String getAccessTokenFromPreferences();

    String getRefreshTokenFromPreferences();

    String refreshAccessToken();
}
