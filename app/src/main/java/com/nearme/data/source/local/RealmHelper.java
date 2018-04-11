package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.ArrayList;

/**
 * Created by xnorcode on 07/04/2018.
 */

public interface RealmHelper {


    /**
     * @param places a list of places
     * @return save operation status
     */
    boolean savePlaces(ArrayList<Place> places);


    /**
     * @return list of all places
     */
    ArrayList<Place> getPlaces();


    /**
     * @param id the place ID
     * @return the place by it's ID
     */
    Place getPlace(String id);


    /**
     * @return delete operation status
     */
    boolean deleteAll();
}
