package ru.aydarov.randroid.data.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class RedditUtilsNetTest {

    @Test
    public void getHttpBasicAuthHeader() {
        Map<String, String> httpBasicAuthHeader = RedditUtilsNet.getHttpBasicAuthHeader();
        Assert.assertEquals(1, httpBasicAuthHeader.size());
        Assert.assertNotNull(httpBasicAuthHeader.get(RedditUtilsNet.AUTHORIZATION_KEY));
    }

    @Test
    public void getOAuthHeader() {
        Map<String, String> oAuthHeader = RedditUtilsNet.getOAuthHeader("1234");
        Assert.assertEquals(2, oAuthHeader.size());
        Assert.assertTrue(oAuthHeader.get(RedditUtilsNet.USER_AGENT_KEY).contains(RedditUtilsNet.USER_AGENT));
        Assert.assertTrue(oAuthHeader.get(RedditUtilsNet.AUTHORIZATION_KEY).contains("1234"));

    }

    @Test
    public void getParamsAuth() {
        Map<String, String> paramsAuth = RedditUtilsNet.getParamsAuth("1234");
        Assert.assertEquals(3, paramsAuth.size());
        Assert.assertTrue(paramsAuth.get(RedditUtilsNet.RESPONSE_TYPE).contains("1234"));
    }

    @Test
    public void getParamsRefresh() {
        Map<String, String> paramsRefresh = RedditUtilsNet.getParamsRefresh("1234");
        Assert.assertEquals(2, paramsRefresh.size());
        Assert.assertTrue(paramsRefresh.get(RedditUtilsNet.REFRESH_TOKEN_KEY).contains("1234"));
    }
}