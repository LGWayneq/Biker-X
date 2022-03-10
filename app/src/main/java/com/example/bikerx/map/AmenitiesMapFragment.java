package com.example.bikerx.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.R;
import com.example.bikerx.control.ApiManager;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.ui.fullmap.FullMapFragment;
import com.example.bikerx.ui.session.CyclingSessionViewModel;
import com.example.bikerx.ui.session.CyclingSessionViewModelFactory;
import com.example.bikerx.ui.session.SessionSummaryFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AmenitiesMapFragment extends MapFragment {
    private ApiManager apiManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        super.onMapReady(map);
        this.apiManager = new ApiManager();
        if (getParentFragment() instanceof SessionSummaryFragment) displayBicycleRacks();
        if (getParentFragment() instanceof FullMapFragment) displayAmenities();
    }

    public void getAmenities(){

    }

    public void getSearchedAmenity(String amenityName){}

    private void displayBicycleRacks() {
        apiManager.getBicycleRacks(super.getMap());
    }

    private void displayAmenities() {
        apiManager.getAmenities(super.getMap());
    }

   // private void displayFilteredAmenities(ArrayList<String>){ }

    private void displaySearchedAmenity(String amenityName){}



}
