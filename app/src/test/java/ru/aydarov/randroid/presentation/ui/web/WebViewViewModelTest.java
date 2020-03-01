package ru.aydarov.randroid.presentation.ui.web;

import android.net.Uri;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import dagger.Lazy;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.aydarov.randroid.data.model.Token;
import ru.aydarov.randroid.data.repository.api.token.RedditTokenAPI;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryToken;
import ru.aydarov.randroid.data.repository.repo.token.RepositoryTokenImpl;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.token.TokenInteractor;
import ru.aydarov.randroid.domain.token.TokenInteractorImpl;
import ru.aydarov.randroid.domain.util.SchedulersProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, SchedulersProvider.class})
public class WebViewViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RedditTokenAPI mTokenAPI;
    @Mock
    Lazy<RedditTokenAPI> mRedditTokenAPILazy;
    private WebViewViewModel viewModel;
    @Mock
    Observer<WebViewViewModel.State> observer;
    @Mock
    Uri uri;
    @Mock
    private TokenInteractorImpl mTokenInteractor;

    @Mock
    private RepositoryTokenImpl mRepositoryToken;
    @Mock
    Lazy<RepositoryToken> mRepositoryTokenLazy;
    @Mock
    Lazy<TokenInteractor> mTokenInteractorLazy;
    private Map<String, String> mOAuthHeader;
    private Map<String, String> mParamsAuth;
    private Single<Token> mTokenSingle;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Uri.class);
        PowerMockito.mockStatic(SchedulersProvider.class);
        PowerMockito.when(Uri.class, "parse", anyString()).thenReturn(uri);
        PowerMockito.when(SchedulersProvider.class, "io").thenReturn(Schedulers.trampoline());
        when(uri.getQueryParameter(anyString())).thenReturn("123");
        viewModel = new WebViewViewModel(mTokenInteractorLazy);
        when(mTokenInteractorLazy.get()).thenReturn(mTokenInteractor);
        when(mRepositoryTokenLazy.get()).thenReturn(mRepositoryToken);
        when(mRedditTokenAPILazy.get()).thenReturn(mTokenAPI);
        viewModel.getState().observeForever(observer);
        mOAuthHeader = RedditUtilsNet.getHttpBasicAuthHeader();
        mParamsAuth = RedditUtilsNet.getParamsAuth("123");

    }

    @Test
    public void testNull() {
        mTokenSingle = Single.just(new Token("123", "321"));
        when(mTokenAPI.getAccessToken(mOAuthHeader, mParamsAuth)).thenReturn(mTokenSingle);
        when(mRepositoryToken.getToken(mOAuthHeader, mParamsAuth))
                .thenReturn(mTokenSingle);
        doReturn(true).when(mTokenInteractor).checkUrlAndGetTokens(viewModel.getState(), "?code=123");


        assertNotNull(viewModel.getState());
        assertTrue(viewModel.getState().hasObservers());
    }

    @Test
    public void testSuccess() {
        mTokenSingle = Single.just(new Token("123", "321"));
        when(mTokenAPI.getAccessToken(mOAuthHeader, mParamsAuth)).thenReturn(mTokenSingle);
        when(mRepositoryToken.getToken(mOAuthHeader, mParamsAuth))
                .thenReturn(mTokenSingle);

        verify(observer).onChanged(WebViewViewModel.State.NONE);
        viewModel.checkUrlAndGetTokens("?code=123");
        verify(mTokenInteractor).checkUrlAndGetTokens(viewModel.getState(), "?code=123");
    }

    @Test
    public void testError() {
        mTokenSingle = Single.error(new Throwable("Hi_it_me"));
        when(mTokenAPI.getAccessToken(mOAuthHeader, mParamsAuth)).thenReturn(mTokenSingle);
        when(mRepositoryToken.getToken(mOAuthHeader, mParamsAuth))
                .thenReturn(mTokenSingle);

        doReturn(true).when(mTokenInteractor).checkUrlAndGetTokens(viewModel.getState(), "?code=123");
        verify(observer).onChanged(WebViewViewModel.State.NONE);
        viewModel.checkUrlAndGetTokens("?code=123");
        verify(mTokenInteractor).checkUrlAndGetTokens(viewModel.getState(), "?code=123");
    }

    @Test
    public void testGetUrl() {
        when(mTokenInteractor.getUrl()).thenReturn("url");
        String url = viewModel.getUrl();
        assertEquals("url", url);

    }
}