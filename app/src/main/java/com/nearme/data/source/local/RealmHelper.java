package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.List;

import io.realm.Realm;

/**
 * Created by xnorcode on 07/04/2018.
 */

public interface RealmHelper {


    /**
     * @param places a list of places
     * @return save operation status
     */
    boolean savePlaces(Realm realm, List<Place> places);


    /**
     * @return list of all places
     */
    List<Place> getPlaces(Realm realm);


    /**
     * @param id the place ID
     * @return the place by it's ID
     */
    Place getPlace(Realm realm, String id);


    /**
     * @return delete operation status
     */
    boolean deleteAll(Realm realm);
}
