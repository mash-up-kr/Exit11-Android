package io.mashup.exit11.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public class Coordinate {
    @SerializedName("Latitude")
    private float latitude;

    @SerializedName("Longitude")
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
