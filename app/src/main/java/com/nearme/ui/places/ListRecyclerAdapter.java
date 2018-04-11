package com.nearme.ui.places;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearme.R;
import com.nearme.data.Place;
import com.nearme.utils.PlacesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListViewHolder> {


    // List of places
    private ArrayList<Place> mPlaces;


    // user's current location
    private Location mCurrentLocation;


    // places activity presenter
    private PlacesContract.View mMvpView;


    /**
     * Register activity view
     *
     * @param view of places activity
     */
    public ListRecyclerAdapter(PlacesContract.View view) {
        mMvpView = view;
    }


    /**
     * Add places and current user's location in adapter
     *
     * @param places list
     */
    public void swapData(ArrayList<Place> places, Location location) {
        mPlaces = places;
        mCurrentLocation = location;
        notifyDataSetChanged();
    }


    /**
     * Release memory refs
     */
    public void destroy() {
        mPlaces = null;
        mCurrentLocation = null;
        mMvpView = null;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (mPlaces == null || mPlaces.size() == 0) return;
        // get place from adapter list
        Place place = mPlaces.get(position);
        // show place icon image
        Picasso.get().load(place.getIconImage()).into(holder.mIcon);
        // show place name
        holder.mName.setText(place.getName());
        // generate place info
        StringBuilder sb = new StringBuilder(place.getType());
        sb.append(" address ");
        sb.append(place.getAddress());
        sb.append(" which is located aprox. ");
        // calculate distance
        Location placeLocation = PlacesHelper.getLocation(place.getLat(), place.getLng());
        String distance = PlacesHelper.calculateFriendlyFormattedDistance(mCurrentLocation, placeLocation);
        sb.append(distance);
        sb.append(" from you");
        // display place info
        holder.mInfo.setText(sb.toString());
        // set onClick listener for each view
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass place id to details activity
                mMvpView.openDetailsActivity(place.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mPlaces == null) return 0;
        return mPlaces.size();
    }
}
