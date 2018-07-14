package com.nearme.data.source.remote;

import com.nearme.data.Place;

import java.util.List;

/**
 * Created by xnorcode on 06/04/2018.
 */

public interface GooglePlacesApiHelper {


    /**
     * @param lat the latitude
     * @param lng the longitude
     * @return a list of nearby places
     */
    List<Place> getNearbyBars(double lat, double lng);


    /**
     * @param name the name of place to be searched
     * @return a list of places
     */
    List<Place> searchPlace(String name);

}
