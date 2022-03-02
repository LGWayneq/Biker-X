package com.example.bikerx.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.control.LocationManager;
import com.example.bikerx.databinding.FragmentMapBinding;
import com.example.bikerx.ui.session.CyclingSessionViewModel;
import com.example.bikerx.ui.session.CyclingSessionViewModelFactory;
import com.example.bikerx.ui.session.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private boolean locationPermissionGranted;
    private FragmentMapBinding mBinding;
    private GoogleMap map;
    private CyclingSessionViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMapBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new CyclingSessionViewModelFactory(requireContext(), (AppCompatActivity) requireActivity()))
                .get(CyclingSessionViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayMap(savedInstanceState);
    }

    public void displayMap(@Nullable Bundle savedInstanceState) {
        if (viewModel.getLocationPermissionGranted()){
            mBinding.mapView.getMapAsync(this);
            mBinding.mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;
        locationPermissionGranted = viewModel.getLocationPermissionGranted();
        viewModel.getLocationManager().getDeviceLocation(locationPermissionGranted);
        updateLocationUI();
        moveCamera();
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        map.setMyLocationEnabled(locationPermissionGranted);
        map.getUiSettings().setMyLocationButtonEnabled(locationPermissionGranted);
        if (locationPermissionGranted) {
            //move my location button to bottom right;
            View locationButton = ((View) mBinding.mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
    }

    private void moveCamera() {
        float DEFAULT_ZOOM = 15.0F;
        final Observer<LatLng> locationObserver = new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng latLng) {
                //map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            }
        };
        viewModel.getLocationManager().getLiveLocation().observe(this, locationObserver);
    }


    public CyclingSessionViewModel getViewModel() {
        return viewModel;
    }

    public GoogleMap getMap() {
        return map;
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