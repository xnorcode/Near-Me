package com.nearme.data.source.remote.utils;

import java.net.URL;

import okhttp3.HttpUrl;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class UrlBuilder {


    /**
     * Build Url to find nearby bars
     * URL sample: https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=lat,lng&radius=1000&types=bar&key=YOUR_API_KEY
     *
     * @param lat    latitude
     * @param lng    longitude
     * @param apiKey for Google Places API
     * @return URL query for nearby bars
     */
    public static URL nearbyBarsUrl(double lat, double lng, String apiKey) {
        // build location query string
        StringBuilder sb = new StringBuilder(String.valueOf(lat));
        sb.append(",");
        sb.append(String.valueOf(lng));

        return new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegments("maps/api/place/nearbysearch/json")
                .addEncodedQueryParameter("location", sb.toString())
                .addQueryParameter("radius", "1000")
                .addQueryParameter("types", "bar")
                .addQueryParameter("key", apiKey)
                .build()
                .url();
    }


    /**
     * Build Url to search for a place name
     * URL sample: https://maps.googleapis.com/maps/api/place/textsearch/json?query=The+Wellington+Hotel+London&key=YOUR_API_KEY
     *
     * @param placeName: String of a place name description
     * @param apiKey     for Google Places API
     * @return URL query to search place
     */
    public static URL searchPlaceUrl(String placeName, String apiKey) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegments("maps/api/place/textsearch/json")
                .addQueryParameter("query", placeName)
                .addQueryParameter("key", apiKey)
                .build()
                .url();
    }

}
