package com.nearme.utils;

import android.location.Location;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class PlacesHelper {


    /**
     * Create android location
     *
     * @param lat the latitude
     * @param lng the longitude
     * @return android location
     */
    public static Location getLocation(double lat, double lng) {
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }


    /**
     * Calculate distance of two locations in a user friendly format
     *
     * @param fromLocation the location to calculate distance from
     * @param toLocation   the location to calculate distance to
     * @return the distance in a user friendly format
     */
    public static String calculateFriendlyFormattedDistance(Location fromLocation, Location toLocation) {
        if (fromLocation == null || toLocation == null) return "";
        // calculate distance
        int distance = Math.round(fromLocation.distanceTo(toLocation));
        // simplify distance
        String units = "m";
        if (distance > 999) {
            distance = distance / 1000;
            units = "km";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(distance));
        sb.append(units);
        // return distance string
        return sb.toString();
    }

}
