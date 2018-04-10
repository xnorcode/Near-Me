package com.nearme.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nearme.R;
import com.nearme.data.Place;

/**
 * Created by xnorcode on 10/04/2018.
 */

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        // remove theme's activity background
        getWindow().setBackgroundDrawable(null);

        if (savedInstanceState == null) {

            DetailsFragment fragment = new DetailsFragment();

            // get extra from intent and add to fragment as argument
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(Place.Constants.ID)) {
                Bundle args = new Bundle();
                args.putString(Place.Constants.ID, intent.getStringExtra(Place.Constants.ID));
                fragment.setArguments(args);
            }

            // add fragment
            getSupportFragmentManager().beginTransaction().add(R.id.details_fragment_container, fragment).commit();

        }
    }

}
