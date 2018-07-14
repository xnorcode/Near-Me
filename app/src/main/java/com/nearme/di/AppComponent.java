package com.nearme.di;

import android.app.Application;

import com.nearme.NearMeApplication;
import com.nearme.data.source.PlacesRepositoryModule;
import com.nearme.location.LocationManagerModule;
import com.nearme.ui.details.DetailsModule;
import com.nearme.ui.places.PlacesModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by xnorcode on 15/05/2018.
 */
@Singleton
@Component(modules = {PlacesRepositoryModule.class,
        LocationManagerModule.class,
        PlacesModule.class,
        DetailsModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<NearMeApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
