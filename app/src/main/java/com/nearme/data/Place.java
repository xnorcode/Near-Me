package com.nearme.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class Place extends RealmObject {


    public class Constants {

        public static final String ID = "place_id";

    }


    // Google Places API place ID
    @PrimaryKey
    private String id;


    // Full name
    private String name;


    // Full formatted address
    private String address;


    // Icon url
    private String iconImage;


    // Type
    private String type;


    // Latitude
    private double lat;


    // Longitude
    private double lng;


    /**
     * Default Constructor
     */
    public Place() {
    }


    /**
     * Constructor
     *
     * @param id for place ID
     */
    public Place(String id) {
        this.id = id;
    }


    /**
     * Getter Methods
     */
    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getIconImage() {
        return iconImage;
    }


    public String getType() {
        return type;
    }


    public double getLat() {
        return lat;
    }


    public double getLng() {
        return lng;
    }


    /**
     * Setter Methods
     */
    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }


    public void setLng(double lng) {
        this.lng = lng;
    }

}
