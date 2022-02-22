package com.example.bikerx.ui.session;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class CyclingSessionViewModel extends ViewModel {
    private MutableLiveData<LatLng> liveLocation;
    private ArrayList<LatLng> locations;
    private LocationManager locationManager;
    private MutableLiveData<Float> distance;
    private boolean locationPermissionGranted = false;

    public void initialiseSession(Context context) {
        locationManager = new LocationManager(context);
        locationPermissionGranted = locationManager.checkLocationPermission();
        if (locationPermissionGranted){
            /*Task<Location> locationResult = locationManager.getLastLocation();
            locationResult.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        locations.add(latLng);
                        liveLocation.setValue(latLng);
                    } else {

                    }
                }
            });*/

            distance = new MutableLiveData<Float>(0.0F);
        }
    }

    public MutableLiveData<Float> getDistance() {
        return distance;
    }
}