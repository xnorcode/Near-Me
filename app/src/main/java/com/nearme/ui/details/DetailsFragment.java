package com.nearme.ui.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearme.R;
import com.nearme.data.Place;
import com.nearme.data.source.PlacesRepository;
import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xnorcode on 10/04/2018.
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {


    @BindView(R.id.details_place_name)
    TextView mName;


    @BindView(R.id.details_place_icon)
    ImageView mIcon;


    @BindView(R.id.details_place_address)
    TextView mAddress;


    // Details Activity Presenter
    private DetailsPresenter mDetailsPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init details presenter
        mDetailsPresenter = new DetailsPresenter(new PlacesRepository(new RealmHelperImpl(),
                new GooglePlacesApiHelperImpl(getString(R.string.google_api_key))),
                new CompositeDisposable());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        // register view of presenter
        mDetailsPresenter.registerView(this);
        // get place id from arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey(Place.Constants.ID)) {
            // get place from local cache
            mDetailsPresenter.getPlace(args.getString(Place.Constants.ID));
        }
    }


    /**
     * Show place details
     *
     * @param place list
     */
    @Override
    public void showPlace(Place place) {
        // display name
        mName.setText(place.getName());
        // display address
        mAddress.setText(place.getAddress());
        // display place icon
        Picasso.get().load(place.getIconImage()).into(mIcon);
    }


    /**
     * Display error
     *
     * @param msg of the error
     */
    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
