package com.example.bikerx.ui.session;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Session {
    private String formattedDistance;
    private LatLng currentLocation;
    private List<LatLng> userPath ;
    public Session(String formattedDistance, LatLng currentLocation, List<LatLng> userPath){
        this.formattedDistance = formattedDistance;
        this.currentLocation = currentLocation;
        this.userPath = userPath;
    }
    public String getFormattedDistance() { return formattedDistance; }
    public LatLng getCurrentLocation() { return currentLocation; }
    public List<LatLng> getUserPath() { return userPath; }
    public void setFormattedDistance(String formattedDistance) { this.formattedDistance = formattedDistance; }
    public void setCurrentLocation(LatLng currentLocation) { this.currentLocation = currentLocation; }
    public void setUserPath(List<LatLng> userPath) { this.userPath = userPath; }
}
