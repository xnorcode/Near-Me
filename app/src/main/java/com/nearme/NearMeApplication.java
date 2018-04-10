package com.nearme;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class NearMeApplication extends Application {

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
}
