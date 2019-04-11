package com.hazelcast.demo.iot.data;

import java.io.Serializable;

public class Location implements Serializable {
    private long longitude;
    private long lattitude;

    Location(long longitude, long lattitude) {
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    public long getLattitude() {
        return lattitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
