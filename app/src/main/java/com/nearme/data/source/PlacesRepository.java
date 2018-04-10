package com.nearme.data.source;

import com.nearme.data.Place;
import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;
import com.nearme.data.source.remote.utils.ApiSource;
import com.nearme.data.source.remote.utils.JsonTool;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.Response;

/**
 * Created by xnorcode on 09/04/2018.
 */

public class PlacesRepository implements PlacesDataSource {


    private final RealmHelperImpl mDbHelper;

    private final GooglePlacesApiHelperImpl mApiHelper;


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
    public Observable<Boolean> downloadAndCacheNearbyBars(@NonNull double lat, @NonNull double lng) throws IOException {
        return mApiHelper.getNearbyBars(lat, lng)
                .map(responseData -> {
                    // get response json string
                    String json = ((Response) responseData).body().string();
                    // extract data from response and return list of places
                    return JsonTool.extractPlaces(json, ApiSource.SEARCH);
                })
                .doOnNext(places -> mDbHelper.deleteAll())
                .flatMap(mDbHelper::savePlaces);

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
    public Observable<Boolean> searchAndCachePlace(@NonNull String name) throws IOException {
        return mApiHelper.searchPlace(name)
                .map(responseData -> {
                    // get response json string
                    String json = ((Response) responseData).body().string();
                    // extract data from response and return list of places
                    return JsonTool.extractPlaces(json, ApiSource.SEARCH);
                })
                .doOnNext(places -> mDbHelper.deleteAll())
                .flatMap(mDbHelper::savePlaces);
    }


    /**
     * Get all places in local data source
     *
     * @return list of places
     */
    @Override
    public Observable<ArrayList<Place>> getPlaces() {
        return mDbHelper.getPlaces();
    }


    /**
     * Get place by it's ID from local data source
     *
     * @param id of a place
     * @return operation status
     */
    @Override
    public Observable<Place> getPlace(String id) {
        return mDbHelper.getPlace(id);
    }


    /**
     * Clear all places in local data source
     *
     * @return operation status
     */
    @Override
    public Observable<Boolean> clearCache() {
        return mDbHelper.deleteAll();
    }

}
