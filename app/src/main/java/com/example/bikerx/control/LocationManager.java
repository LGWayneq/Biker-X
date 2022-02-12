package com.example.bikerx.control;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bikerx.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class LocationManager {
    private FragmentActivity activity;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;
    public LocationManager(FragmentActivity activity){
        this.activity = activity;
    }
    public LocationManager(Context context){
        this.context = context;
    }
    public void getLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                activity.registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = getOrDefault(result,
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = getOrDefault(result,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {

                            } else if (coarseLocationGranted != null && coarseLocationGranted) {

                            } else {

                            }
                        }
                );

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog.
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    public Task<Location> getLastLocation() {
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (checkLocationPermission()) return fusedLocationProviderClient.getLastLocation();
        return null;
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public <K, V> V getOrDefault(@NonNull Map<K, V> map, K key, V defaultValue) {
        V v;
        return (((v = map.get(key)) != null) || map.containsKey(key))
                ? v
                : defaultValue;
    }
}
