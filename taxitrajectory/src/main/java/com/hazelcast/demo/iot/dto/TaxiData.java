package com.hazelcast.demo.iot.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TaxiData implements Serializable {

    private String ID;
    private Timestamp timestamp;
    private Location location;

    public TaxiData(String ID, Timestamp timestamp, Location location) {
        this.ID = ID;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getID() {
        return ID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return ID + " < > " + timestamp + " < > " + location;
    }
}

