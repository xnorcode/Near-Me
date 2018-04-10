package com.nearme.ui.base;

/**
 * Created by xnorcode on 09/04/2018.
 */

public interface BasePresenter<V> {


    /**
     * Bind view with presenter
     *
     * @param view the view of this presenter
     */
    void registerView(V view);


    /**
     * Removes reference of the view
     */
    void dropView();

}
