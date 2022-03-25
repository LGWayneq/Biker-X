//IN PROGRESS
package com.example.bikerx.ui.fullmap;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.R;

import com.example.bikerx.control.ApiManager;
import com.example.bikerx.databinding.FragmentFullMapBinding;
import com.example.bikerx.map.AmenitiesMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class FullMapFragment extends Fragment {

    private FullMapViewModel fullMapViewModel;
    private FragmentFullMapBinding binding;
    private GoogleMap map;
    private ApiManager apiManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fullMapViewModel =
                new ViewModelProvider(this).get(FullMapViewModel.class);

        binding = FragmentFullMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        ArrayList<String> checkedAmenities = new ArrayList<String>();


        ImageView mToggleButton = (ImageView) view.findViewById(R.id.toggleButton);

        LinearLayout mVisibleCheckBoxes = view.findViewById(R.id.checkBoxLayout);

        CheckBox mCheckBicycleRacks = view.findViewById(R.id.checkBox_bicyclerack);
        CheckBox mCheckBicycleRentalShop = view.findViewById(R.id.checkBox_bicyclerentalshop);
        CheckBox mCheckFitnessArea = view.findViewById(R.id.checkBox_fitnessarea);
        CheckBox mCheckFNBEatery = view.findViewById(R.id.checkBox_fnbeatery);
        CheckBox mCheckHawkerCentre = view.findViewById(R.id.checkBox_hawkercentre);
        CheckBox mCheckPlayground = view.findViewById(R.id.checkBox_playground);
        CheckBox mCheckShelter = view.findViewById(R.id.checkBox_shelter);
        CheckBox mCheckToilet = view.findViewById(R.id.checkBox_toilet);
        CheckBox mCheckWaterCooler = view.findViewById(R.id.checkBox_watercooler);

        boolean isCheckedBicycleRacks = mCheckBicycleRacks.isChecked();
        boolean isCheckedBicycleRentalShop = mCheckBicycleRentalShop.isChecked();
        boolean isCheckedFitnessArea = mCheckFitnessArea.isChecked();
        boolean isCheckedFNBEatery = mCheckFNBEatery.isChecked();
        boolean isCheckedHawkerCentre = mCheckHawkerCentre.isChecked();
        boolean isCheckedPlayground = mCheckPlayground.isChecked();
        boolean isCheckedShelter = mCheckShelter.isChecked();
        boolean isCheckedToilet = mCheckToilet.isChecked();
        boolean isCheckedWaterCooler = mCheckWaterCooler.isChecked();

       // ArrayList<Marker> FitnessAreaMarkerList = apiManager.getAmenitiesData(map, "FITNESS_AREA");


        mToggleButton.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View view) {
                visible = !visible;
                mVisibleCheckBoxes.setVisibility(visible ? View.VISIBLE: View.GONE);
            }
        });

        mCheckBicycleRacks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckBicycleRacks.isChecked()){
                    mCheckBicycleRacks.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckBicycleRacks.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckBicycleRentalShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckBicycleRentalShop.isChecked()){
                    mCheckBicycleRentalShop.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckBicycleRentalShop.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckFitnessArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckFitnessArea.isChecked()){
                    mCheckFitnessArea.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckFitnessArea.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });


        mCheckFNBEatery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckFNBEatery.isChecked()){
                    mCheckFNBEatery.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckFNBEatery.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckHawkerCentre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckHawkerCentre.isChecked()){
                    mCheckHawkerCentre.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckHawkerCentre.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckPlayground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckPlayground.isChecked()){
                    mCheckPlayground.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckPlayground.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckShelter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckShelter.isChecked()){
                    mCheckShelter.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckShelter.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckToilet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckToilet.isChecked()){
                    mCheckToilet.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckToilet.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckWaterCooler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckedBicycleRacks) {
                if (mCheckWaterCooler.isChecked()){
                    mCheckWaterCooler.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckWaterCooler.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}