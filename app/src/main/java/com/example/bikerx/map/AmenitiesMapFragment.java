package com.example.bikerx.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.bikerx.control.ApiManager;
import com.example.bikerx.ui.fullmap.FullMapFragment;
import com.example.bikerx.ui.session.SessionSummaryFragment;
import com.google.android.gms.maps.GoogleMap;

public class AmenitiesMapFragment extends MapFragment {
    private ApiManager apiManager;
    private MutableLiveData<Boolean> mapReady = new MutableLiveData<>(false);

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
        this.mapReady.setValue(true);
    }

    public void displayBicycleRacks() {
        apiManager.getBicycleRacks(super.getMap());
    }

    public MutableLiveData<Boolean> getMapReady() {
        return this.mapReady;
    }

}
