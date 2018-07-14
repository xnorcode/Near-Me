package com.nearme.data.source.remote;

import com.nearme.data.Place;
import com.nearme.data.source.GoogleApiKey;
import com.nearme.data.source.remote.utils.ApiSource;
import com.nearme.data.source.remote.utils.JsonTool;
import com.nearme.data.source.remote.utils.UrlBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xnorcode on 06/04/2018.
 */
@Singleton
public class GooglePlacesApiHelperImpl implements GooglePlacesApiHelper {


    private String apiKey;


    @Inject
    public GooglePlacesApiHelperImpl(@GoogleApiKey String key) {
        this.apiKey = key;
    }


    /**
     * Download nearby bars
     *
     * @param lat the latitude
     * @param lng the longitude
     * @return the list with the nearby bars
     */
    @Override
    public List<Place> getNearbyBars(double lat, double lng) {
        try {
            // create url
            String url = UrlBuilder.nearbyBarsUrl(lat, lng, apiKey).toString();
            // init OKHttp client
            OkHttpClient client = new OkHttpClient();
            // create request
            Request request = new Request.Builder().url(url).build();
            // execute network request call
            Response response = client.newCall(request).execute();
            // check status
            if (response.code() != 200) return null;
            // get response json string
            String json = response.body().string();
            // extract data from response and return list of places
            return JsonTool.extractPlaces(json, ApiSource.NEARBY);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Download the searched place
     *
     * @param name the name of place to be searched
     * @return the list places
     */
    @Override
    public List<Place> searchPlace(String name) {
        try {
            // create url
            String url = UrlBuilder.searchPlaceUrl(name, apiKey).toString();
            // init OKHttp client
            OkHttpClient client = new OkHttpClient();
            // create request
            Request request = new Request.Builder().url(url).build();
            // return network request call
            Response response = client.newCall(request).execute();
            // check status
            if (response.code() != 200) return null;
            // get response json string
            String json = response.body().string();
            // extract data from response and return list of places
            return JsonTool.extractPlaces(json, ApiSource.SEARCH);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
