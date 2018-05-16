package com.nearme.location;

import android.location.Location;

import com.google.android.gms.common.api.Status;

/**
 * Created by xnorcode on 06/04/2018.
 */

public interface LocationManager {


    /**
     * @param callback to activity
     */
    void connect(Callback callback);


    void disconnect();


    /**
     * @return latest location
     */
    Location getLocation();


    /**
     * Location Manager Callback
     */
    interface Callback {


        void onLocationManagerConnected();


        /**
         * @param status of Google Location API
         */
        void onLocationResolution(Status status);

    }

}
