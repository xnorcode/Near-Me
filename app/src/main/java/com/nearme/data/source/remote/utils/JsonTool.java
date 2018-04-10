package com.nearme.data.source.remote.utils;

import com.nearme.data.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class JsonTool {


    /**
     * Extract list of places from the Google Places API query response
     *
     * @param json   String of the network response
     * @param source selector of the api query response
     * @return list with downloaded places
     * @throws JSONException JSON error
     */
    public static ArrayList<Place> extractPlaces(String json, @ApiSource.SOURCE int source) throws JSONException {
        // init output list
        ArrayList<Place> output = new ArrayList<>();
        if (json == null || json.isEmpty()) return output;
        JSONObject obj = new JSONObject(json);
        if (!obj.has("results")) return output;
        JSONArray results = obj.getJSONArray("results");
        if (results.length() == 0) return output;
        // create places and add to output list
        for (int i = 0; i < results.length(); i++) {
            // get place json object
            JSONObject p = results.getJSONObject(i);
            // create Place object with ID
            Place place = new Place(p.getString("id"));
            // get name
            place.setName(p.getString("name"));
            // get address
            switch (source) {
                case ApiSource.NEARBY:
                    place.setAddress(p.getString("vicinity"));
                    break;
                case ApiSource.SEARCH:
                    place.setAddress(p.getString("formatted_address"));
                    break;
            }
            // get icon image url
            place.setIconImage(p.getString("icon"));
            // get type
            if (p.has("types")) {
                JSONArray types = p.getJSONArray("types");
                if (types.length() > 0) {
                    place.setType(types.getString(0));
                }
            }
            // set location
            if (p.has("geometry")) {
                JSONObject geometry = p.getJSONObject("geometry");
                if (geometry.has("location")) {
                    JSONObject loc = geometry.getJSONObject("location");
                    place.setLat(loc.getDouble("lat"));
                    place.setLng(loc.getDouble("lng"));
                }
            }
            //add to list
            output.add(place);
        }
        // return list with Places
        return output;
    }

}
