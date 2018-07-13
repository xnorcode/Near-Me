package com.nearme.data.source;

import android.app.Application;

import com.nearme.R;
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
    RealmHelperImpl provideLocalDataSource() {
        return new RealmHelperImpl();
    }


    @Singleton
    @Provides
    GooglePlacesApiHelperImpl provideRemoteDataSource(@GoogleApiKey String apiKey) {
        return new GooglePlacesApiHelperImpl(apiKey);
    }


    @Singleton
    @Provides
    @GoogleApiKey
    String providePlacesApiKey(Application context) {
        return context.getApplicationContext().getString(R.string.google_api_key);
    }
}
