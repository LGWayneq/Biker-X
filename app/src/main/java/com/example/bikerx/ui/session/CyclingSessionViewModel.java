package com.example.bikerx.ui.session;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.control.LocationManager;
import com.google.android.gms.maps.model.LatLng;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class CyclingSessionViewModel extends ViewModel {

    private LocationManager locationManager;
    private boolean locationPermissionGranted;
    private final Session EMPTY = new Session("0.00", null, new ArrayList<LatLng>());
    private MutableLiveData<Session> session = new MutableLiveData<Session>(EMPTY);
    private DBManager dbManager = new DBManager();
    private AppCompatActivity activity;

    public CyclingSessionViewModel(Context context, AppCompatActivity activity) {
        this.activity = activity;
        this.locationManager = new LocationManager(context);
        this.locationPermissionGranted = locationManager.checkLocationPermission();
    }

    public void initialiseSession(AppCompatActivity activity) {
        startTracking();
        if (locationPermissionGranted){
            locationManager.getLiveLocations().observe(activity, new Observer<ArrayList<LatLng>>() {
                @Override
                public void onChanged(ArrayList<LatLng> locations) {
                    Session current = session.getValue();
                    if (current != null) {
                        current.setUserPath(locations);
                        session.setValue(current);
                    }
                }
            });
            locationManager.getLiveLocation().observe(activity, new Observer<LatLng>() {
                @Override
                public void onChanged(LatLng currentLocation) {
                    Session current = session.getValue();
                    if (current != null) {
                        current.setCurrentLocation(currentLocation);
                        session.setValue(current);
                    }
                }
            });
            locationManager.getLiveDistance().observe(activity, new Observer<Double>() {
                @Override
                public void onChanged(Double distance) {
                    Session current = session.getValue();
                    String formattedDistance = activity.getString(R.string.distance_value, distance);
                    if (current != null) {
                        current.setFormattedDistance(formattedDistance);
                        session.setValue(current);
                    }
                }
            });
        }
    }

    public void startTracking() { locationManager.trackUser(); }
    public void pauseTracking() { locationManager.pauseTracking(); }
    public void resumeTracking() { locationManager.resumeTracking(); }
    public void stopTracking(long duration) {
        Session session = this.session.getValue();
        Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault());
        dbManager.addCyclingSession(((MainActivity)activity).getUserId(), dateFormat.format(date), session.getFormattedDistance(), duration);

        this.session.setValue(new Session("0.00", null, new ArrayList<LatLng>()));
        locationManager.stopTracking();
    }

    public MutableLiveData<Session> getSession() {
        return session;
    }

    public LocationManager getLocationManager() {
        return this.locationManager;
    }

    public boolean getLocationPermissionGranted() {
        return locationPermissionGranted;
    }
}