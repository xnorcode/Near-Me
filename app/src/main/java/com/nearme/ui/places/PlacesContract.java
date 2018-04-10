package com.nearme.ui.places;

import com.nearme.data.Place;
import com.nearme.ui.base.BasePresenter;
import com.nearme.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by xnorcode on 09/04/2018.
 */

public class PlacesContract {


    interface View extends BaseView<Presenter> {


        /**
         * @param places ArrayList of all places
         */
        void showPlaces(ArrayList<Place> places);


        /**
         * @param msg error message
         */
        void showError(String msg);


        /**
         * @param id place ID
         */
        void openDetailsActivity(String id);

    }


    interface Presenter extends BasePresenter<View> {


        /**
         * @param lat the latitude
         * @param lng the longitude
         */
        void getNearbyBars(double lat, double lng);


        /**
         * @param placeName String of place to search
         */
        void searchPlace(String placeName);


        void loadPlaces();

    }

}
