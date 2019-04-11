package com.hazelcast.demo.iot.data;

import java.io.Serializable;

public class Location implements Serializable {
    private Double longitude;
    private Double latitude;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
