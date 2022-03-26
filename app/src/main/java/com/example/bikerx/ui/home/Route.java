package com.example.bikerx.ui.home;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.HashMap;

public class Route {

    private Integer imageId;
    private String routeId;
    private String routeName;
    private ArrayList<HashMap<String, Object>> ratings;
    private ArrayList<LatLng> coordinates;

    public Route(Integer imageId, String routeId, String routeName, ArrayList<HashMap<String, Object>> ratings, ArrayList<LatLng> coordinates){
        this.imageId = imageId;
        this.routeId = routeId;
        this.routeName = routeName;
        this.ratings = ratings;
        this.coordinates = coordinates;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public ArrayList<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<HashMap<String, Object>> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<HashMap<String, Object>> ratings) {
        this.ratings = ratings;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
