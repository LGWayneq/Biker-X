package com.example.bikerx.map;

enum AmenityType {
    BICYCLE_RACK,
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
    private AmenityType type;
    private double latitude;
    private double longitude;

    public Amenity(String name, String type, double latitude, double longitude) {
        this.name = name;
        //this.type = AmenityType.BICYCLE_RACK; //TO BE ADDED

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
