//IN PROGRESS
package com.example.bikerx.ui.fullmap;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bikerx.R;

import java.util.ArrayList;
public class FilterAmenityActivity extends AppCompatActivity {
    public TextView amenities;
    public boolean[] selectedAmenities;
    public static ArrayList<AmenityType> amenitiesList = new ArrayList<AmenityType>();

    /*
    String[] AmenityType =
            {"SHELTER","BICYCLE_RACK","BICYCLE_RENTAL_SHOP","RENTAL_SHOP","FITNESS_AREA","FNB_EATERY",
            "HAWKER_CENTRE","PLAYGROUND","PLAYGROUND","TOILET","WATER_COOLER"};

     */

    private ListView listView;
    private ArrayAdapter<CharSequence> sortAdapter;
    private Spinner sortDropdownlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_full_map);

        setUpData();
        setUpList();
        setUpOnClickListener();
    }

    private void setUpData(){
        for (AmenityType amenity :AmenityType.values()){
            amenitiesList.add(amenity);
        }
    }

    private void setUpList(){
        listView = (ListView) findViewById(R.id.amenitiesListView);

        AmenityAdapter adapter = new AmenityAdapter(getApplicationContext(), 0, amenitiesList);

    }

    private void setUpOnClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Amenity selectAmenity = (Amenity) (listView.getItemAtPosition(position));
             /*   Intent showDetail = new Intent(getApplicationContext(), );
                startActivity(showDetail); */
            }
        });
    }
}
