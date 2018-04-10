package com.nearme.data.source.remote;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.Response;

/**
 * Created by xnorcode on 06/04/2018.
 */

public interface GooglePlacesApiHelper {


    /**
     * @param lat the latitude
     * @param lng the longitude
     * @return OkHttp network call response
     * @throws IOException for network call
     */
    Observable<Response> getNearbyBars(@NonNull double lat, @NonNull double lng) throws IOException;


    /**
     * @param name the name of place to be searched
     * @return OkHttp network call response
     * @throws IOException for network call
     */
    Observable<Response> searchPlace(@NonNull String name) throws IOException;

}
