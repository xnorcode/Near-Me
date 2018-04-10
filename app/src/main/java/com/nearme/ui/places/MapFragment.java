package com.nearme.ui.places;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nearme.data.Place;
import com.nearme.ui.details.DetailsActivity;

import java.util.ArrayList;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, PlacesContract.View {


    // places activity presenter
    private PlacesContract.Presenter mPresenter;


    // user's current location
    private Location mCurrentLocation;


    // Google map
    private GoogleMap mGoogleMap;


    @Override
    public void onStart() {
        super.onStart();
        // init google map
        getMapAsync(this);
    }


    @Override
    public void onStop() {
        super.onStop();

        // clear and remove google map ref
        if (mGoogleMap != null) mGoogleMap.clear();
        mGoogleMap = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.dropView();

        mCurrentLocation = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // get ready map
        mGoogleMap = googleMap;
    }


    /**
     * Draw places pins on map
     *
     * @param places ArrayList of all places
     */
    @Override
    public void showPlaces(ArrayList<Place> places) {
        drawMap(places, mCurrentLocation);
    }


    /**
     * Open details activity
     *
     * @param id place ID
     */
    @Override
    public void openDetailsActivity(String id) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(Place.Constants.ID, id);
        startActivity(intent);
    }


    /**
     * Display error
     *
     * @param msg error message
     */
    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * Draw places on Google map
     *
     * @param places
     */
    @SuppressLint("MissingPermission")
    private void drawMap(ArrayList<Place> places, Location userLocation) {
        if (mGoogleMap == null || places == null || places.size() == 0) return;
        // clear previous markers
        mGoogleMap.clear();
        // Show a "move to my location" button
        mGoogleMap.setMyLocationEnabled(true);
        // Drop marker on map for each place
        for (Place place : places) {
            // set on marker click listener
            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // pass place id into details activity
                    openDetailsActivity(marker.getSnippet());
                    return true;
                }
            });
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.getLat(), place.getLng()))
                    .title(place.getName())
                    .snippet(place.getId()));
        }
        // Move camera to users location and zoom out at a scale of 13.
        if (userLocation == null) return;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 13));
    }
}
