package com.example.bikerx.control;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bikerx.map.Amenity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class ApiManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void getAmenities(GoogleMap map) {
        db.collection("amenities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                MarkerOptions marker = new MarkerOptions();
                                LatLng latLng = new LatLng((Double) data.get("latitude"), (Double) data.get("longitude"));
                                map.addMarker(marker.position(latLng).title("%s - %s".format((String)data.get("name"), (String)data.get("type"))).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                            }
                        }
                    }
                });
    }



    public void getBicycleRacks(GoogleMap map) {
        db.collection("bicycle-racks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                MarkerOptions marker = new MarkerOptions();
                                LatLng latLng = new LatLng((Double) data.get("latitude"), (Double) data.get("longitude"));
                                map.addMarker(marker.position(latLng).title("BICYCLE RACK"));
                            }
                        }
                    }
                });
    }

    public void loadBicycleRacks(AppCompatActivity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("bicycle-rack.geojson");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            class BicycleRack {
                Double latitude;
                Double longitude;
                public BicycleRack(Double lat, Double lng) { this.latitude = lat; this.longitude = lng; }

                public Double getLatitude() {
                    return latitude;
                }

                public Double getLongitude() {
                    return longitude;
                }

                public void setLatitude(Double latitude) {
                    this.latitude = latitude;
                }

                public void setLongitude(Double longitude) {
                    this.longitude = longitude;
                }
            }
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("features");
            for (int i = 0; i<jsonArray.length(); i++){
                JSONArray coords = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                db.collection("bicycle-racks").add(new BicycleRack(coords.getDouble(0), coords.getDouble(1)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
