package com.nearme.data.source;

import com.nearme.data.Place;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

/**
 * Created by xnorcode on 09/04/2018.
 */

public interface PlacesDataSource {


    /**
     * @param lat the latitude
     * @param lng the longitude
     * @return operation status
     * @throws IOException for network call
     */
    Flowable<Boolean> downloadAndCacheNearbyBars(@NonNull double lat, @NonNull double lng) throws IOException;


    /**
     * @param name the name of place to be searched
     * @return operation status
     * @throws IOException for network call
     */
    Flowable<Boolean> searchAndCachePlace(@NonNull String name) throws IOException;


    /**
     * @return a list of places
     */
    Flowable<ArrayList<Place>> getPlaces();


    /**
     * @param id of a place
     * @return place by id
     */
    Flowable<Place> getPlace(@NonNull String id);


    /**
     * @return operation status
     */
    Flowable<Boolean> clearCache();

}
