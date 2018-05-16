package com.nearme;

import com.nearme.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class NearMeApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // init realmdb
        Realm.init(this);
        // default configs
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("placesdb")
                .schemaVersion(1)
                .build();
        // set default configs
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
