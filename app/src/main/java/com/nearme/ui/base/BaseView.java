package com.nearme.ui.base;

/**
 * Created by xnorcode on 09/04/2018.
 */

public interface BaseView<V> {

    /**
     * @param presenter of current view
     */
    void setPresenter(V presenter);

}
