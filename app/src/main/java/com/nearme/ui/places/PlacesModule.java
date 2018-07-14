package com.nearme.ui.places;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 16/05/2018.
 */
@Module
public abstract class PlacesModule {


    @ContributesAndroidInjector
    abstract PlacesActivity contributeActivityInjector();

    @Binds
    abstract PlacesContract.Presenter placesPresenter(PlacesPresenter presenter);

}
