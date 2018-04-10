package com.nearme.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nearme.R;

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

        if (savedInstanceState == null){
            DetailsFragment fragment = new DetailsFragment();

            // get intents

            Intent intent = getIntent();

        }
    }

}
