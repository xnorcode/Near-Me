package com.nearme.ui.places;

import com.nearme.data.source.PlacesRepository;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xnorcode on 09/04/2018.
 */

public class PlacesPresenter implements PlacesContract.Presenter {


    // Places Activity View
    private PlacesContract.View mView;


    // Places repository for local and remote transactions
    private PlacesRepository mPlacesRepository;


    // RxJava disposable manager for all Rx operations
    private CompositeDisposable mCompositeDisposable;

    // current location
    private double lat;
    private double lng;


    /**
     * Constructor
     *
     * @param mPlacesRepository    for local and remote transactions
     * @param mCompositeDisposable for managing all Rx operations
     */
    public PlacesPresenter(PlacesRepository mPlacesRepository, CompositeDisposable mCompositeDisposable) {
        this.mPlacesRepository = mPlacesRepository;
        this.mCompositeDisposable = mCompositeDisposable;
    }


    /**
     * Register a view
     *
     * @param view of presenter
     */
    @Override
    public void setView(PlacesContract.View view) {
        mView = view;
    }


    /**
     * Release all memory references
     */
    @Override
    public void dropView() {
        mView = null;
        mCompositeDisposable.clear();
        mPlacesRepository = null;
    }


    /**
     * Get all nearby bars
     *
     * @param lat the latitude
     * @param lng the longitude
     */
    @Override
    public void getNearbyBars(double lat, double lng) {
        try {
            Disposable disposable = mPlacesRepository.downloadAndCacheNearbyBars(lat, lng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(status -> {
                        if (mView != null) mView.onDownloadCompleted();
                    });
            mCompositeDisposable.add(disposable);
        } catch (IOException e) {
            e.printStackTrace();
            mView.showError(e.getMessage());
        }
    }


    /**
     * Search for a specific place
     *
     * @param placeName query to search
     */
    @Override
    public void searchPlace(String placeName) {
        try {
            Disposable disposable = mPlacesRepository.searchAndCachePlace(placeName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(status -> {
                        if (mView != null) mView.onDownloadCompleted();
                    });
            mCompositeDisposable.add(disposable);
        } catch (IOException e) {
            e.printStackTrace();
            mView.showError(e.getMessage());
        }
    }


    /**
     * Set current user's location
     *
     * @param lat the latitude
     * @param lng the longitude
     */
    @Override
    public void setLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }


    /**
     * Load all places from cache
     */
    @Override
    public void loadPlaces() {
        Disposable disposable = mPlacesRepository.getPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(places -> {
                    if (mView != null) mView.showPlaces(places, lat, lng);
                });
        mCompositeDisposable.add(disposable);
    }
}
