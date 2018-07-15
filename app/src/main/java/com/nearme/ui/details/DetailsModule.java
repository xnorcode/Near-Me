package com.nearme.ui.details;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 15/05/2018.
 */
@Module
public abstract class DetailsModule {

    @ContributesAndroidInjector
    abstract DetailsFragment contributeFragmentInjector();

    @ContributesAndroidInjector
    abstract DetailsActivity contributesActivityInjector();

    @Binds
    abstract DetailsContract.Presenter detailsPresenter(DetailsPresenter presenter);

}