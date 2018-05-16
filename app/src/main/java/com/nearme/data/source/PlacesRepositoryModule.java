package com.nearme.data.source;

import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 16/05/2018.
 */
@Module
public class PlacesRepositoryModule {


    @Singleton
    @Provides
    @RealmDB
    RealmHelperImpl provideLocalDataSource() {
        return new RealmHelperImpl();
    }


    @Singleton
    @Provides
    @PlacesAPI
    GooglePlacesApiHelperImpl provideRemoteDataSource() {
        return new GooglePlacesApiHelperImpl();
    }
}
