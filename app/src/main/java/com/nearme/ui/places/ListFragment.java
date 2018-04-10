package com.nearme.ui.places;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nearme.R;
import com.nearme.data.Place;
import com.nearme.ui.details.DetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class ListFragment extends Fragment implements PlacesContract.View {


    // places activity presenter
    private PlacesContract.Presenter mPresenter;


    // recycler view reference
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    // recycler view's adapter
    private ListRecyclerAdapter mRecyclerAdapter;


    // user's current location
    private Location mCurrentLocation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, rootView);

        // set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // init recycler adapter and pass the View reference
        mRecyclerAdapter = new ListRecyclerAdapter(this);
        // pass adapter to recycler view
        mRecyclerView.setAdapter(mRecyclerAdapter);

        return rootView;
    }


    /**
     * Release memory references
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.dropView();

        if (mRecyclerView != null) mRecyclerView.setAdapter(null);
        mRecyclerView = null;

        if (mRecyclerAdapter != null) mRecyclerAdapter.destroy();
        mRecyclerAdapter = null;

        mCurrentLocation = null;
    }


    /**
     * Pass list of places and current user's location
     * in recycler adapter
     *
     * @param places ArrayList of all places
     */
    @Override
    public void showPlaces(ArrayList<Place> places) {
        mRecyclerAdapter.swapData(places, mCurrentLocation);
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

}
