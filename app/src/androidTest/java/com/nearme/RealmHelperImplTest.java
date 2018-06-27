package com.nearme;

import android.support.test.runner.AndroidJUnit4;

import com.nearme.data.Place;
import com.nearme.data.source.local.RealmHelperImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by xnorcode on 12/04/2018.
 */
@RunWith(AndroidJUnit4.class)
public class RealmHelperImplTest {

    RealmHelperImpl mDbHelper;
    RealmConfiguration testConfig;
    ArrayList<Place> places;

    @Before
    public void setup() {

        // init database
        mDbHelper = new RealmHelperImpl();

        // create realm test configuration
        testConfig = new RealmConfiguration.Builder().inMemory().name("test-realm").build();

        // clear database
        mDbHelper.deleteAll(Realm.getInstance(testConfig));

        // create mock place
        places = new ArrayList<>();
        Place place1 = new Place("1234");
        place1.setName("Name of place");
        place1.setAddress("address here");
        place1.setIconImage("url here");
        place1.setType("bar");
        place1.setLat(0.0);
        place1.setLng(0.1);
        places.add(place1);
    }

    @Test
    public void savePlaces() {

        // verify places are ready for storing
        Assert.assertTrue(places.size() > 0);

        // save mock place
        boolean result = mDbHelper.savePlaces(Realm.getInstance(testConfig), places);

        // check result
        Assert.assertTrue(result);
    }

    @Test
    public void getPlaces() {

        // get original place
        Place place1 = places.get(0);

        // verify place
        Assert.assertTrue(place1 != null);

        // get places from realm
        ArrayList<Place> result = mDbHelper.getPlaces(Realm.getInstance(testConfig));

        // Verify realm results
        Assert.assertNotNull(result);

        // get place from results
        Place place2 = result.get(0);

        // check results id against original
        Assert.assertEquals(place1.getId(), place2.getId());
    }

    @Test
    public void getPlace() {

        // Get place from realmDB
        Place result = mDbHelper.getPlace(Realm.getInstance(testConfig), "1234");

        // Check place
        Assert.assertNotNull(result);

        // Check place id
        Assert.assertEquals("1234", result.getId());

    }

    @Test
    public void deleteAll() {

        // clear database
        boolean result = mDbHelper.deleteAll(Realm.getInstance(testConfig));

        // check result
        Assert.assertTrue(result);

    }

}