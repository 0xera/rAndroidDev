package ru.aydarov.randroid.domain.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dagger.Lazy;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import ru.aydarov.randroid.data.util.RedditUtilsNet;
import ru.aydarov.randroid.domain.token.TokenInteractor;
import ru.aydarov.randroid.domain.token.TokenInteractorImpl;

import static org.mockito.Mockito.when;

public class AccessTokenAuthenticatorTest {

    @Mock
    Request mRequest;
    @Mock
    Response mResponse;
    @Mock
    Headers mHeaders;

    @Mock
    TokenInteractorImpl mTokenInteractor;
    AccessTokenAuthenticator mAccessTokenAuthenticator;

    @Mock
    Request.Builder mBuilder;
    @Mock
    Headers.Builder mBuilderHead;
    @Mock
    HeaderGenerator mHeaderGenerator;
    @Mock
    Lazy<TokenInteractor> mTokenInteractorLazy;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mAccessTokenAuthenticator = new AccessTokenAuthenticator(mTokenInteractorLazy, mHeaderGenerator);
        when(mResponse.request()).thenReturn(mRequest);
        when(mTokenInteractorLazy.get()).thenReturn(mTokenInteractor);
        when(mResponse.request().newBuilder()).thenReturn(mBuilder);
        when(mHeaders.newBuilder()).thenReturn(mBuilderHead);
        when(mBuilder.build()).thenReturn(mRequest);
        when(mHeaderGenerator.getHeaders(mBuilder, "123456")).thenReturn(mRequest);

    }


    @Test
    public void authenticate() {
        when(mTokenInteractor.refreshAccessToken()).thenReturn("123456");
        when(mTokenInteractor.getAccessTokenFromPreferences()).thenReturn("123456");
        when(mResponse.request()).thenReturn(mRequest);
        when(mRequest.header(RedditUtilsNet.AUTHORIZATION_KEY)).thenReturn("baerer=123456");
        when(mResponse.code()).thenReturn(401);
        Request authenticate = mAccessTokenAuthenticator.authenticate(null, mResponse);
        Assert.assertNotNull(authenticate);

    }

    @Test
    public void authenticate1() {
        when(mTokenInteractor.refreshAccessToken()).thenReturn("");
        when(mTokenInteractor.getAccessTokenFromPreferences()).thenReturn("123456");
        when(mResponse.request()).thenReturn(mRequest);
        when(mRequest.header(RedditUtilsNet.AUTHORIZATION_KEY)).thenReturn("baerer=123456");
        when(mResponse.code()).thenReturn(401);
        Request authenticate = mAccessTokenAuthenticator.authenticate(null, mResponse);
        Assert.assertNull(authenticate);

    }

    @Test
    public void authenticate11() {
        when(mTokenInteractor.refreshAccessToken()).thenReturn("");
        when(mTokenInteractor.getAccessTokenFromPreferences()).thenReturn("123456");
        when(mResponse.request()).thenReturn(mRequest);
        when(mRequest.header(RedditUtilsNet.AUTHORIZATION_KEY)).thenReturn("");
        when(mResponse.code()).thenReturn(401);
        Request authenticate = mAccessTokenAuthenticator.authenticate(null, mResponse);
        Assert.assertNull(authenticate);

    }

    @Test
    public void authenticate2() {
        when(mTokenInteractor.refreshAccessToken()).thenReturn("123");
        when(mTokenInteractor.getRefreshTokenFromPreferences()).thenReturn("123456");
        when(mTokenInteractor.getAccessTokenFromPreferences()).thenReturn("654321");
        when(mResponse.request()).thenReturn(mRequest);
        when(mRequest.header(RedditUtilsNet.AUTHORIZATION_KEY)).thenReturn("baerer=123456");
        when(mResponse.code()).thenReturn(401);
        Request authenticate = mAccessTokenAuthenticator.authenticate(null, mResponse);
        Assert.assertNotNull(authenticate);

    }

    @Test
    public void authenticateNull() {
        when(mTokenInteractor.refreshAccessToken()).thenReturn("123");
        when(mTokenInteractor.getRefreshTokenFromPreferences()).thenReturn("123");
        when(mResponse.code()).thenReturn(404);
        Request authenticate = mAccessTokenAuthenticator.authenticate(null, mResponse);
        Assert.assertNull(authenticate);

    }
}

