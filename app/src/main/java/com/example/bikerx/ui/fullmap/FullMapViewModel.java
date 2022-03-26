package com.example.bikerx.ui.fullmap;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.ApiManager;
import com.example.bikerx.map.AmenitiesMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class FullMapViewModel extends ViewModel {

    protected ArrayList<Marker> AccessPointMarkerList = null;
    protected ArrayList<Marker> BicycleRacksMarkerList = null;
    protected ArrayList<Marker> BicycleRentalShopMarkerList = null;
    protected ArrayList<Marker> FitnessAreaMarkerList = null;
    protected ArrayList<Marker> FNBEateryMarkerList = null;
    protected ArrayList<Marker> HawkerCentreMarkerList = null;
    protected ArrayList<Marker> PlaygroundMarkerList = null;
    protected ArrayList<Marker> ShelterMarkerList = null;
    protected ArrayList<Marker> ToiletMarkerList = null;
    protected ArrayList<Marker> WaterCoolerMarkerList = null;

    private GoogleMap map;
    private ApiManager apiManager;
   // private AppCompatActivity activity;


    public void initialiseMarkers(FullMapFragment fragment){
        this.apiManager = new ApiManager();
     //   this.activity = (AppCompatActivity) fragment.getActivity();
       // apiManager.loadBicycleRacksIntoAmenities(activity);

        if (PlaygroundMarkerList == null){
            AmenitiesMapFragment amenitiesMapFragment = (AmenitiesMapFragment) fragment.getChildFragmentManager().getFragments().get(0);
            map = amenitiesMapFragment.getMap();
            map.clear();

            apiManager.getAmenitiesData(map,"ACCESS POINT").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    AccessPointMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"BICYCLE RACK").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    BicycleRacksMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"BICYCLE RENTAL SHOP").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    BicycleRentalShopMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"FITNESS AREA").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    FitnessAreaMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"F&B").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    FNBEateryMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"HAWKER CENTRE").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    HawkerCentreMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"PLAYGROUND").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    PlaygroundMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"SHELTER").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    ShelterMarkerList = markers;
                }
            });

            apiManager.getAmenitiesData(map,"TOILET").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    ToiletMarkerList = markers;

                }
            });

            apiManager.getAmenitiesData(map,"WATER POINT").observe(fragment.getViewLifecycleOwner(), new Observer<ArrayList<Marker>>() {
                @Override
                public void onChanged(ArrayList<Marker> markers) {
                    WaterCoolerMarkerList = markers;

                }
            });

        }
    }

    public void setMarkerVisibility(ArrayList<Marker> markerList, boolean checked){
        for (Marker m : markerList){
            m.setVisible(checked); }
    }

}