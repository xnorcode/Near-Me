package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class RealmHelperImpl implements RealmHelper {


    /**
     * Save all places
     *
     * @param places a list of places
     * @return save operation status
     */
    @Override
    public boolean savePlaces(ArrayList<Place> places) {
        // get realm instance
        Realm realm = Realm.getDefaultInstance();
        try {
            // begin write transaction
            realm.beginTransaction();
            realm.insert(places);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close realm instance
            realm.close();
        }
    }


    /**
     * Get all places
     *
     * @return list of all places
     */
    @Override
    public ArrayList<Place> getPlaces() {
        // get realm instance
        Realm realm = Realm.getDefaultInstance();
        try {
            // begin read query
            RealmResults<Place> results = realm.where(Place.class).findAll();
            ArrayList<Place> places = (ArrayList<Place>) realm.copyFromRealm(results);
            return places;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close realm instance
            realm.close();
        }
    }


    /**
     * Get place by it's ID
     *
     * @param id the place ID
     * @return the place by it's ID
     */
    @Override
    public Place getPlace(String id) {
        // get realm instance
        Realm realm = Realm.getDefaultInstance();
        try {
            // begin read query
            Place place = realm.where(Place.class).equalTo("id", id).findFirst();
            return realm.copyFromRealm(place);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close realm instance
            realm.close();
        }
    }


    /**
     * Delete all places
     *
     * @return delete operation status
     */
    @Override
    public boolean deleteAll() {
        // get realm instance
        Realm realm = Realm.getDefaultInstance();
        try {
            // begin delete transaction
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close realm instance
            realm.close();
        }
    }
}
