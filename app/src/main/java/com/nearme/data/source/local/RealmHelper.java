package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by xnorcode on 07/04/2018.
 */

public interface RealmHelper {


    /**
     * @param places a list of places
     * @return save operation status
     */
    Observable<Boolean> savePlaces(@NonNull ArrayList<Place> places);


    /**
     * @return list of all places
     */
    Observable<ArrayList<Place>> getPlaces();


    /**
     * @param id the place ID
     * @return the place by it's ID
     */
    Observable<Place> getPlace(@NonNull String id);


    /**
     * @return delete operation status
     */
    Observable<Boolean> deleteAll();
}
