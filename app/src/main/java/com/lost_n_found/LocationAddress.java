package com.lost_n_found;

public class LocationAddress {

    public String place;
    public double lat;
    public double lon;

    public LocationAddress(String place , double lat, double lon) {
        this.place = place;
        this.lat = lat;
        this.lon = lon;
    }

    public String getPlace() {
        return place;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
