package com.nearme.data.source;

import com.nearme.data.Place;
import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;
import com.nearme.data.source.remote.utils.ApiSource;
import com.nearme.data.source.remote.utils.JsonTool;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import okhttp3.Response;

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
    public Flowable<Boolean> downloadAndCacheNearbyBars(@NonNull double lat, @NonNull double lng) throws IOException {
        return Flowable.just(mApiHelper.getNearbyBars(lat, lng))
                .map(call -> {
                    // execute network call
                    Response response = call.execute();
                    // get response json string
                    String json = response.body().string();
                    // extract data from response and return list of places
                    ArrayList<Place> places = JsonTool.extractPlaces(json, ApiSource.NEARBY);
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
    public Flowable<Boolean> searchAndCachePlace(@NonNull String name) throws IOException {
        return Flowable.just(mApiHelper.searchPlace(name))
                .map(call -> {
                    // execute network call
                    Response response = call.execute();
                    // get response json string
                    String json = response.body().string();
                    // extract data from response and return list of places
                    ArrayList<Place> places = JsonTool.extractPlaces(json, ApiSource.SEARCH);
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
    public Flowable<ArrayList<Place>> getPlaces() {
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
