package com.nearme.data.source.remote;

import com.nearme.data.source.remote.utils.UrlBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by xnorcode on 06/04/2018.
 */

public class GooglePlacesApiHelperImpl implements GooglePlacesApiHelper {


    // Google Places API key
    private String apiKey;


    /**
     * Constructor
     *
     * @param apiKey for Google Places API
     */
    public GooglePlacesApiHelperImpl(String apiKey) {
        this.apiKey = apiKey;
    }


    /**
     * Download nearby bars
     *
     * @param lat the latitude
     * @param lng the longitude
     * @return OkHttp network call response
     * @throws IOException for network call
     */
    @Override
    public Call getNearbyBars(double lat, double lng) throws IOException {
        // create url
        String url = UrlBuilder.nearbyBarsUrl(lat, lng, apiKey).toString();
        // init OKHttp client
        OkHttpClient client = new OkHttpClient();
        // create request
        Request request = new Request.Builder().url(url).build();
        // execute network request call
        return client.newCall(request);
    }


    /**
     * Download the searched place
     *
     * @param name the name of place to be searched
     * @return OkHttp network call response
     * @throws IOException for network call
     */
    @Override
    public Call searchPlace(String name) throws IOException {
        // create url
        String url = UrlBuilder.searchPlaceUrl(name, apiKey).toString();
        // init OKHttp client
        OkHttpClient client = new OkHttpClient();
        // create request
        Request request = new Request.Builder().url(url).build();
        // return network request call
        return client.newCall(request);
    }
}
