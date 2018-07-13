package com.nearme.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

/**
 * Created by xnorcode on 06/04/2018.
 */

public class LocationManagerImpl implements LocationManager, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // for the location settings request
    public static final int REQUEST_CODE_CHECK_LOCATION_SETTINGS = 800;

    // Location API related
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder mLocationRequestSettings;
    private PendingResult<LocationSettingsResult> mResult;

    // Location Manager Callback
    private Callback mCallback;

    // Current location
    private Location mCurrentLocation;

    // Location Manager connection status
    private boolean ready;

    Context mContext;


    @Inject
    public LocationManagerImpl(Context context) {
        mContext = context;
    }


    /**
     * Connect to Location API
     *
     * @param callback to activity
     */
    @Override
    public void connect(Callback callback) {
        // register callback
        this.mCallback = callback;
        // connect Google Location API
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.reconnect();
        }
    }


    /**
     * Get current user's location
     *
     * @return Android Location
     */
    @Override
    public Location getLocation() {
        return mCurrentLocation;
    }


    /**
     * Disconnect Manager and Release memory refs
     */
    @Override
    public void disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        mLocationRequest = null;
        mLocationRequestSettings = null;
        mResult = null;
        mCallback = null;
        mCurrentLocation = null;
        mContext = null;
    }


    /**
     * Location API Callback Methods
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // create the location request
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // updating location every half second.
        mLocationRequest.setInterval(200);
        mLocationRequestSettings = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        mResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationRequestSettings.build());
        mResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationManagerImpl.this);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // UserLocation settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        if (mCallback != null) {
                            mCallback.onLocationResolution(status);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // UserLocation settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    /**
     * Handle errors
     */
    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /**
     * Update mCurrentLocation
     *
     * @param location of user
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (ready || mCurrentLocation == null) return;
        ready = true;
        if (mCallback != null) {
            mCallback.onLocationManagerConnected();
        }
    }
}
