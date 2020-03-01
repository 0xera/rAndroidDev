package ru.aydarov.randroid.presentation.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import ru.aydarov.randroid.data.model.Media;
import ru.aydarov.randroid.data.model.Preview;
import ru.aydarov.randroid.data.model.RedditPost;

public class RedditUtilsTest {


    private RedditPost sPost = Mockito.mock(RedditPost.class);
    private Preview.Image.Source sSource = Mockito.mock(Preview.Image.Source.class);


    private Media sMedia = Mockito.mock(Media.class);
    private Preview.Image sImage = Mockito.mock(Preview.Image.class);

    private Media.Video sVideo = Mockito.mock(Media.Video.class);
    private Preview sPreview = Mockito.mock(Preview.class);
    private Preview.Image.Resolution sReso = Mockito.mock(Preview.Image.Resolution.class);
    private Preview.Image.Resolution sReso2 = Mockito.mock(Preview.Image.Resolution.class);
    ;


    @Before
    public void setUp() {
        Mockito.when(sPost.getUrl()).thenReturn("Mockito.any");
        Mockito.when(sPost.getPostHint()).thenReturn("rich:video");
        Mockito.when(sPost.getPreview()).thenReturn(sPreview);
        Mockito.when(sPost.getMedia()).thenReturn(sMedia);
        Mockito.when(sMedia.getVideo()).thenReturn(sVideo);


    }

    @Test
    public void getUrl() {
        String url = RedditUtils.getUrl("1234?4321");
        Assert.assertEquals("1234", url);
    }

    @Test
    public void isRichVideo() {
        Assert.assertTrue(RedditUtils.isLinkOrRichVideo(sPost));
    }

    @Test
    public void isRichVideoOrLink() {
        Assert.assertTrue(RedditUtils.isLinkOrRichVideo(sPost));
    }

    @Test
    public void isLink() {
        Assert.assertFalse(RedditUtils.isLink(sPost));
    }

    @Test
    public void isSelfText() {
        Assert.assertFalse(RedditUtils.isSelfText(sPost));
    }

    @Test
    public void isImage() {
        Assert.assertFalse(RedditUtils.isImage(sPost));
    }

    @Test
    public void isVideo() {
        Assert.assertTrue(RedditUtils.isVideo(sPost));
    }

    @Test
    public void getUrlPreview() {
        Mockito.when(sPreview.getImages()).thenReturn(Arrays.asList(sImage));
        Mockito.when(sImage.getSource()).thenReturn(sSource);
        Mockito.when(sSource.getUrl()).thenReturn("url1");
        Mockito.when(sImage.getResolutions()).thenReturn(Arrays.asList(sReso, sReso2, sReso));
        Mockito.when(sReso.getHeight()).thenReturn(0);
        Mockito.when(sReso.getWidth()).thenReturn(0);
        Mockito.when(sReso2.getHeight()).thenReturn(1);
        Mockito.when(sReso2.getWidth()).thenReturn(1);
        Mockito.when(sReso2.getUrl()).thenReturn("url2");
        String urlPreview = RedditUtils.getUrlPreview(sPost);
        Assert.assertEquals("url2", urlPreview);
    }

    @Test
    public void getUrlPreview12() {
        Mockito.when(sPreview.getImages()).thenReturn(Arrays.asList(sImage));
        Mockito.when(sImage.getSource()).thenReturn(sSource);
        Mockito.when(sSource.getUrl()).thenReturn("url1");
        Mockito.when(sImage.getResolutions()).thenReturn(Collections.emptyList());
        Mockito.when(sReso.getHeight()).thenReturn(0);
        Mockito.when(sReso.getWidth()).thenReturn(0);
        Mockito.when(sReso2.getHeight()).thenReturn(1);
        Mockito.when(sReso2.getWidth()).thenReturn(1);
        Mockito.when(sReso.getUrl()).thenReturn("url2");
        Assert.assertNull(RedditUtils.getUrlPreview(sPost));
    }

    @Test
    public void getUrlPreview2() {
        Mockito.when(sPreview.getImages()).thenReturn(Arrays.asList(sImage));
        Mockito.when(sImage.getSource()).thenReturn(sSource);
        Mockito.when(sSource.getUrl()).thenReturn("url1");
        Mockito.when(sImage.getResolutions()).thenReturn(null);
        Mockito.when(sReso.getHeight()).thenReturn(0);
        Mockito.when(sReso.getWidth()).thenReturn(0);
        Mockito.when(sReso.getUrl()).thenReturn(null);
        String urlPreview = RedditUtils.getUrlPreview(sPost);
        Assert.assertEquals("url1", urlPreview);
    }

    @Test
    public void getUrlPreview21() {
        Mockito.when(sPreview.getImages()).thenReturn(Arrays.asList(sImage));
        Mockito.when(sImage.getSource()).thenReturn(sSource);
        Mockito.when(sSource.getUrl()).thenReturn("url1");
        Mockito.when(sImage.getResolutions()).thenReturn(null);
        Mockito.when(sReso.getHeight()).thenReturn(0);
        Mockito.when(sReso.getWidth()).thenReturn(0);
        Mockito.when(sReso.getUrl()).thenReturn(null);
        Assert.assertNull(RedditUtils.getUrlPreview(null));
    }


    @Test
    public void getAudioUrl() {
        String url = RedditUtils.getAudioUrl("4321/1234/1234?sss");
        Assert.assertEquals("4321/1234/audio", url);
    }

}