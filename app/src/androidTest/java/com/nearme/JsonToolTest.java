package com.nearme;


import android.support.test.runner.AndroidJUnit4;

import com.nearme.data.Place;
import com.nearme.data.source.remote.utils.ApiSource;
import com.nearme.data.source.remote.utils.JsonTool;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by xnorcode on 10/04/2018.
 */
@RunWith(AndroidJUnit4.class)
public class JsonToolTest {

    public String loadJsonData(String fileName) throws IOException {
        // build body string from SearchHiltonPlace.json file
        InputStream is = getInstrumentation().getContext().getResources().getAssets().open(fileName);
        // read the file
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        // close stream
        is.close();
        return new String(buffer, "UTF-8");
    }

    @Test
    public void extractPlaces() throws Exception {

        // create mock places nearby
        Place expected1 = new Place("b03108bcba9bdbdb69aba86d2f4d0d0a28fdd3a1");
        expected1.setAddress("108 Woolwich Road, London");
        expected1.setType("bar");
        // get places from mock json data
        ArrayList<Place> result1 = JsonTool.extractPlaces(loadJsonData("NearbyBars.json"), ApiSource.NEARBY);
        Place place1 = result1.get(0);
        // compare
        Assert.assertEquals(expected1.getId(), place1.getId());
        Assert.assertEquals(expected1.getAddress(), place1.getAddress());
        Assert.assertEquals(expected1.getType(), place1.getType());

        // create mock places from search
        Place expected2 = new Place("93fbc78806df9fe01be9752d2c90a931b80f526a");
        expected2.setAddress("22 Park Ln, Mayfair, London W1K 1BE, UK");
        expected2.setType("lodging");
        // get places from mock json data
        ArrayList<Place> result2 = JsonTool.extractPlaces(loadJsonData("SearchHiltonPlace.json"), ApiSource.SEARCH);
        Place place2 = result2.get(0);
        // compare
        Assert.assertEquals(expected2.getId(), place2.getId());
        Assert.assertEquals(expected2.getAddress(), place2.getAddress());
        Assert.assertEquals(expected2.getType(), place2.getType());

    }

}