package com.nearme.data.source;

import com.nearme.data.Place;
import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;

/**
 * Created by xnorcode on 09/04/2018.
 */
@Singleton
public class PlacesRepository implements PlacesDataSource {


    // Realm database helper
    private final RealmHelperImpl mDbHelper;


    // Google Places API helper
    private final GooglePlacesApiHelperImpl mApiHelper;


    /**
     * Constructor
     *
     * @param mDbHelper  Realm database helper
     * @param mApiHelper Google Places API helper
     */
    @Inject
    public PlacesRepository(RealmHelperImpl mDbHelper, GooglePlacesApiHelperImpl mApiHelper) {
        this.mDbHelper = mDbHelper;
        this.mApiHelper = mApiHelper;
    }


    /**
     * Download nearby bars from remote data source
     * and save them all in local data source.
     *
     * @param lat the latitude
     * @param lng the longitude
     * @return operation status
     * @throws IOException for network call
     */
    @Override
    public Flowable<Boolean> downloadAndCacheNearbyBars(@NonNull double lat, @NonNull double lng) {
        return Flowable.<List<Place>>create(emitter -> {
            List<Place> places = mApiHelper.getNearbyBars(lat, lng);
            if (places != null)
                emitter.onNext(places);
            else
                emitter.onError(new Exception("Could not get nearby places..."));
            emitter.onComplete();
        }, BackpressureStrategy.ERROR)
                .map(places -> {
                    // clear cache
                    mDbHelper.deleteAll(Realm.getDefaultInstance());
                    // save places to cache
                    return mDbHelper.savePlaces(Realm.getDefaultInstance(), places);
                });
    }


    /**
     * Download searched place from remote data source
     * and save it in local data source.
     *
     * @param name the name of place to be searched
     * @return operation status
     * @throws IOException for network call
     */
    @Override
    public Flowable<Boolean> searchAndCachePlace(@NonNull String name) {
        return Flowable.<List<Place>>create(emitter -> {
            List<Place> places = mApiHelper.searchPlace(name);
            if (places != null)
                emitter.onNext(places);
            else
                emitter.onError(new Exception("Could not find place..."));
            emitter.onComplete();
        }, BackpressureStrategy.ERROR)
                .map(places -> {
                    // clear cache
                    mDbHelper.deleteAll(Realm.getDefaultInstance());
                    // save places to cache
                    return mDbHelper.savePlaces(Realm.getDefaultInstance(), places);
                });
    }


    /**
     * Get all places in local data source
     *
     * @return list of places
     */
    @Override
    public Flowable<List<Place>> getPlaces() {
        return Flowable.just(mDbHelper.getPlaces(Realm.getDefaultInstance()));
    }


    /**
     * Get place by it's ID from local data source
     *
     * @param id of a place
     * @return operation status
     */
    @Override
    public Flowable<Place> getPlace(String id) {
        return Flowable.just(mDbHelper.getPlace(Realm.getDefaultInstance(), id));
    }


    /**
     * Clear all places in local data source
     *
     * @return operation status
     */
    @Override
    public Flowable<Boolean> clearCache() {
        return Flowable.just(mDbHelper.deleteAll(Realm.getDefaultInstance()));
    }

}
