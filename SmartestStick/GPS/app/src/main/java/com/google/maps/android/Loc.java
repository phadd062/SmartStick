package com.google.maps.android;

public class Loc {

    public String label;
    public double lat;
    public double lon;

    public Loc() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Loc(String label, double lat, double lon) {
        this.label = label;
        this.lat = lat;
        this.lon = lon;
    }

}