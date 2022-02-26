package com.example.bikerx.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bikerx.control.ApiManager;
import com.example.bikerx.databinding.FragmentAmenitiesMapBinding;
import com.google.android.gms.maps.GoogleMap;

public class AmenitiesMapFragment extends MapFragment {
    private FragmentAmenitiesMapBinding mBinding;
    private ApiManager apiManager;

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mBinding = FragmentAmenitiesMapBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        super.onMapReady(googleMap);
    }
}
