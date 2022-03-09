package com.example.bikerx.map;

enum AmenityType {
    SHELTER,
    BICYCLE_RACK,
    BICYCLE_RENTAL_SHOP,
    RENTAL_SHOP,
    FITNESS_AREA,
    FNB_EATERY,
    HAWKER_CENTRE,
    PLAYGROUND,
    TOILET,
    WATER_COOLER
}
public class Amenity {
    private String name;
    private String type;
    private double latitude;
    private double longitude;

    public Amenity(String name, String type, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
