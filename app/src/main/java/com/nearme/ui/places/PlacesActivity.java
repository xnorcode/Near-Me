package com.nearme.ui.places;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.nearme.R;
import com.nearme.data.source.PlacesRepository;
import com.nearme.data.source.local.RealmHelperImpl;
import com.nearme.data.source.remote.GooglePlacesApiHelperImpl;
import com.nearme.location.LocationManager;
import com.nearme.location.LocationManagerImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class PlacesActivity extends AppCompatActivity implements LocationManager.Callback {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @BindView(R.id.search_box)
    EditText mSearchBox;


    // PlacesActivity presenter
    private PlacesPresenter mPlacesPresenter;


    // location manager
    private LocationManagerImpl mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // remove theme's activity background
        getWindow().setBackgroundDrawable(null);

        ButterKnife.bind(this);

        // setup TabLayout
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_list)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_map)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // setup ViewPager
        mViewPager.setAdapter(new PlacesPageAdapter(getSupportFragmentManager(), mTabLayout.getTabCount()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        // init places presenter
        mPlacesPresenter = new PlacesPresenter(new PlacesRepository(new RealmHelperImpl(),
                new GooglePlacesApiHelperImpl(getString(R.string.google_api_key))),
                new CompositeDisposable());

        // add tab selection listener
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // pass tab number
                mViewPager.setCurrentItem(tab.getPosition());
                // register view to presenter
                registerCurrentView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // setup search box listener
        mSearchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // search for inserted place
                    mPlacesPresenter.searchPlace(mSearchBox.getText().toString());
                    return true;
                }
                return false;
            }
        });

        // register view to presenter
        registerCurrentView();
    }


    /**
     * Check and request location permissions from the user
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(PlacesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(PlacesActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createLocationPermissionsRequest();
                return;
            }
        }
        // init and connect location manager
        mLocationManager = new LocationManagerImpl();
        mLocationManager.connect(this, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // disconnect location manager and release ref
        if (mLocationManager != null) mLocationManager.disconnect();
        mLocationManager = null;
    }


    /**
     * Get current user's location when location manager
     * is connect and init getNearbyBars request
     */
    @Override
    public void onLocationManagerConnected() {
        Location location = mLocationManager.getLocation();
        mPlacesPresenter.getNearbyBars(location.getLatitude(), location.getLongitude());
    }


    /**
     * Request resolution on location api conflict
     *
     * @param status of Google Location API
     */
    @Override
    public void onLocationResolution(Status status) {
        try {
            status.startResolutionForResult(this, LocationManagerImpl.REQUEST_CODE_CHECK_LOCATION_SETTINGS);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    /**
     * Handle permission request results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // init and connect location manager
            mLocationManager = new LocationManagerImpl();
            mLocationManager.connect(this, this);
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            // request permission
            createLocationPermissionsRequest();
        }
    }


    /**
     * Create location permission request
     */
    @SuppressLint("NewApi")
    private void createLocationPermissionsRequest() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
        }, 10);
    }


    /**
     * Register selected view in presenter
     */
    private void registerCurrentView() {
        // register current fragment in presenter
        int index = mViewPager.getCurrentItem();
        // TODO: 10/04/2018 Bug Fix: Get current fragment and pass to presenter
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + index);
        if (fragment == null) return;
        switch (index) {
            case 0:
                mPlacesPresenter.registerView((ListFragment) fragment);
                break;
            case 1:
                mPlacesPresenter.registerView((MapFragment) fragment);
                break;
        }
    }

}
