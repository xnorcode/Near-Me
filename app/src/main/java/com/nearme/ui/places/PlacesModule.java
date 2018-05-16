package com.nearme.ui.places;

import com.nearme.location.LocationManagerImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by xnorcode on 16/05/2018.
 */
@Module
public abstract class PlacesModule {

    @Binds
    abstract PlacesContract.Presenter placesPresenter(PlacesPresenter presenter);


    @Binds
    abstract LocationManagerImpl locationManager(LocationManagerImpl manager);
}
