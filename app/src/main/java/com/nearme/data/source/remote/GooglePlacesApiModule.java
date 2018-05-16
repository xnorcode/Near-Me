package com.nearme.data.source.remote;

import android.app.Application;

import com.nearme.R;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 16/05/2018.
 */
@Module
public class GooglePlacesApiModule {


    @Provides
    @PlacesApiKey
    String placesKey(Application context) {
        return context.getString(R.string.google_api_key);
    }

}
