package com.nearme.data.source.local;

import com.nearme.data.Place;

import java.util.ArrayList;

import io.reactivex.Observable;
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
    public Observable<Boolean> savePlaces(ArrayList<Place> places) {
        return Observable.create(emitter -> {
            // get realm instance
            Realm realm = Realm.getDefaultInstance();
            try {
                // begin write transaction
                realm.beginTransaction();
                realm.insert(places);
                realm.commitTransaction();
                emitter.onNext(true);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
            } finally {
                // close realm instance
                realm.close();
            }
            emitter.onComplete();
        });
    }


    /**
     * Get all places
     *
     * @return list of all places
     */
    @Override
    public Observable<ArrayList<Place>> getPlaces() {
        return Observable.create(emitter -> {
            // get realm instance
            Realm realm = Realm.getDefaultInstance();
            try {
                // begin read query
                RealmResults<Place> results = realm.where(Place.class).findAll();
                ArrayList<Place> places = (ArrayList<Place>) realm.copyFromRealm(results);
                emitter.onNext(places);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(null);
            } finally {
                // close realm instance
                realm.close();
            }
            emitter.onComplete();
        });
    }


    /**
     * Get place by it's ID
     *
     * @param id the place ID
     * @return the place by it's ID
     */
    @Override
    public Observable<Place> getPlace(String id) {
        return Observable.create(emitter -> {
            // get realm instance
            Realm realm = Realm.getDefaultInstance();
            try {
                // begin read query
                Place place = realm.where(Place.class).equalTo("id", id).findFirst();
                emitter.onNext(place);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(null);
            } finally {
                // close realm instance
                realm.close();
            }
            emitter.onComplete();
        });
    }


    /**
     * Delete all places
     *
     * @return delete operation status
     */
    @Override
    public Observable<Boolean> deleteAll() {
        return Observable.create(emitter -> {
            // get realm instance
            Realm realm = Realm.getDefaultInstance();
            try {
                // begin delete transaction
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                emitter.onNext(true);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
            } finally {
                // close realm instance
                realm.close();
            }
            emitter.onComplete();
        });
    }
}
