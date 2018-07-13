package com.nearme.location;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 13/07/2018.
 */
@Module
public class LocationManagerModule {

    @Singleton
    @Provides
    LocationManager providesLocationManager(Application application) {
        return new LocationManagerImpl(application.getApplicationContext());
    }
}
