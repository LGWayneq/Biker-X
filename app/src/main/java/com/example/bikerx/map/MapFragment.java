package com.example.bikerx.map;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.LocationManager;
import com.example.bikerx.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.Executor;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding mBinding;
    private LocationManager locationManager;
    private boolean locationPermissionGranted = false;
    private GoogleMap map;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMapBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationManager = new LocationManager(requireContext());
        locationPermissionGranted = locationManager.checkLocationPermission();
        if (locationPermissionGranted){
            mBinding.mapView.getMapAsync(this);
            mBinding.mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;
        updateLocationUI();
        getDeviceLocation();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
       locationPermissionGranted = locationManager.checkLocationPermission();
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (locationPermissionGranted) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);

            //move my location button to bottom right;
            View locationButton = ((View) mBinding.mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        } else {
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            locationManager.getLocationPermission();
        }
    }

    private void getDeviceLocation() {
        float DEFAULT_ZOOM = 15.0F;
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = locationManager.getLastLocation();
                locationResult.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(),
                                            location.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(new LatLng(0,0), DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }
}