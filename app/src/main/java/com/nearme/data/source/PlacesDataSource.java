package com.nearme.data.source;

import com.nearme.data.Place;

import java.util.List;

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
     */
    Flowable<Boolean> downloadAndCacheNearbyBars(@NonNull double lat, @NonNull double lng);


    /**
     * @param name the name of place to be searched
     * @return operation status
     */
    Flowable<Boolean> searchAndCachePlace(@NonNull String name);


    /**
     * @return a list of places
     */
    Flowable<List<Place>> getPlaces();


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
