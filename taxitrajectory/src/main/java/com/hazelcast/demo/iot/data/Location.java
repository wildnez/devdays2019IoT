package com.hazelcast.demo.iot.data;

import java.io.Serializable;

public class Location implements Serializable {
    private double longitude;
    private double latitude;

    Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
