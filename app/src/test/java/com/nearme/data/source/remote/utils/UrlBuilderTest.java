package com.nearme.data.source.remote.utils;

import org.junit.Test;

import java.net.URL;

import okhttp3.HttpUrl;

import static junit.framework.TestCase.*;

/**
 * Created by xnorcode on 10/04/2018.
 */
public class UrlBuilderTest {

    @Test
    public void nearbyBarsUrl() throws Exception {
        URL url = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=0.0,0.0&radius=1000&types=bar&key=1234").url();
        assertEquals(UrlBuilder.nearbyBarsUrl(0.0, 0.0, "1234").toString(), url.toString());
    }

    @Test
    public void searchPlaceUrl() throws Exception {
        URL url = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/textsearch/json?query=My query here&key=1234").url();
        assertEquals(UrlBuilder.searchPlaceUrl("My query here", "1234").toString(), url.toString());
    }

}