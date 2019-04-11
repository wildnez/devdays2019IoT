package com.hazelcast.demo.iot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Location implements Serializable {
    private BigDecimal longitude;
    private BigDecimal lattitude;

    public Location(BigDecimal longitude, BigDecimal lattitude) {
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    public BigDecimal getLattitude() {
        return lattitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return longitude + " < > " + lattitude;
    }
}
