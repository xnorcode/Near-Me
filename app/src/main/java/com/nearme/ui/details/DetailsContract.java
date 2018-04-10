package com.nearme.ui.details;

import com.nearme.data.Place;
import com.nearme.ui.base.BasePresenter;
import com.nearme.ui.base.BaseView;

/**
 * Created by xnorcode on 10/04/2018.
 */

public class DetailsContract {

    interface View extends BaseView<Presenter> {


        /**
         * @param place by id
         */
        void showPlace(Place place);


        /**
         * @param msg of the error
         */
        void showError(String msg);

    }

    interface Presenter extends BasePresenter<View> {


        /**
         * @param id of place
         */
        void getPlace(String id);

    }
}
