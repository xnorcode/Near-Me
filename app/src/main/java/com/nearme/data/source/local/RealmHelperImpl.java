package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by xnorcode on 08/04/2018.
 */
@Singleton
public class RealmHelperImpl implements RealmHelper {


    @Inject
    public RealmHelperImpl() {
    }

    /**
     * Save all places
     *
     * @param places a list of places
     * @return save operation status
     */
    @Override
    public boolean savePlaces(Realm realm, ArrayList<Place> places) {
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
    public ArrayList<Place> getPlaces(Realm realm) {
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
    public Place getPlace(Realm realm, String id) {
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
    public boolean deleteAll(Realm realm) {
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
