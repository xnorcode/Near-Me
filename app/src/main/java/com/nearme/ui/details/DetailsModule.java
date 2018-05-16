package com.nearme.ui.details;

import dagger.Binds;
import dagger.Module;

/**
 * Created by xnorcode on 15/05/2018.
 */
@Module
public abstract class DetailsModule {


    @Binds
    abstract DetailsContract.Presenter detailsPresenter(DetailsPresenter presenter);

}