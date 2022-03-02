package com.example.bikerx.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.bikerx.R;
import com.example.bikerx.control.ApiManager;
import com.example.bikerx.ui.session.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RouteMapFragment extends MapFragment{

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

        super.getViewModel().getSession().observe(this, new Observer<Session>() {
            @Override
            public void onChanged(Session session) {
                drawRoute(session);
            }
        });
    }


    public void drawRoute(Session session) {
        List<LatLng> locations = session.getUserPath();
        PolylineOptions polylineOptions = new PolylineOptions().color(getResources().getColor(R.color.teal_700));
        super.getMap().clear();
        List<LatLng> points = polylineOptions.getPoints();
        points.addAll(locations);
        super.getMap().addPolyline(polylineOptions);
    }

}
