package com.nearme.di;

import android.app.Application;

import com.nearme.NearMeApplication;
import com.nearme.data.source.PlacesRepositoryModule;
import com.nearme.data.source.remote.GooglePlacesApiModule;
import com.nearme.ui.details.DetailsModule;
import com.nearme.ui.places.PlacesModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by xnorcode on 15/05/2018.
 */
@Singleton
@Component(modules = {GooglePlacesApiModule.class,
        PlacesRepositoryModule.class,
        PlacesModule.class,
        DetailsModule.class,
        ApplicationModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<NearMeApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
