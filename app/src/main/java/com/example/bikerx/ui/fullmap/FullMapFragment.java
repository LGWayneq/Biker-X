package com.example.bikerx.ui.fullmap;


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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.R;
import com.example.bikerx.control.ApiManager;
import com.example.bikerx.databinding.FragmentFullMapBinding;
import com.example.bikerx.map.AmenitiesMapFragment;

public class FullMapFragment extends Fragment {

    private FullMapViewModel fullMapViewModel;
    private FragmentFullMapBinding binding;

    private AppCompatActivity activity;
    private ApiManager apiManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fullMapViewModel =
                new ViewModelProvider(this).get(FullMapViewModel.class);

        binding = FragmentFullMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    public void initialiseMarkers(){
        fullMapViewModel.initialiseMarkers(this);

    }

    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        ImageView mToggleButton = (ImageView) view.findViewById(R.id.toggleButton);
        LinearLayout mVisibleCheckBoxes = view.findViewById(R.id.checkBoxLayout);

        CheckBox mCheckAccessPoint = view.findViewById(R.id.checkBox_accesspoint);
        CheckBox mCheckBicycleRacks = view.findViewById(R.id.checkBox_bicyclerack);
        CheckBox mCheckBicycleRentalShop = view.findViewById(R.id.checkBox_bicyclerentalshop);
        CheckBox mCheckFitnessArea = view.findViewById(R.id.checkBox_fitnessarea);
        CheckBox mCheckFNBEatery = view.findViewById(R.id.checkBox_fnbeatery);
        CheckBox mCheckHawkerCentre = view.findViewById(R.id.checkBox_hawkercentre);
        CheckBox mCheckPlayground = view.findViewById(R.id.checkBox_playground);
        CheckBox mCheckShelter = view.findViewById(R.id.checkBox_shelter);
        CheckBox mCheckToilet = view.findViewById(R.id.checkBox_toilet);
        CheckBox mCheckWaterCooler = view.findViewById(R.id.checkBox_watercooler);

        getMapReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean mapReady) {
                if (mapReady) initialiseMarkers();
            }
        });

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View view) {


                visible = !visible;
                mVisibleCheckBoxes.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });

        mCheckAccessPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.AccessPointMarkerList, isChecked);
                if (isChecked){
                    mCheckAccessPoint.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckAccessPoint.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckBicycleRacks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.BicycleRacksMarkerList, isChecked);
                if (isChecked){
                    mCheckBicycleRacks.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckBicycleRacks.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckBicycleRentalShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.BicycleRentalShopMarkerList, isChecked);
                if (isChecked){
                    mCheckBicycleRentalShop.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckBicycleRentalShop.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckFitnessArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.FitnessAreaMarkerList, isChecked);
                if (isChecked){
                    mCheckFitnessArea.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckFitnessArea.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });


        mCheckFNBEatery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.FNBEateryMarkerList, isChecked);
                if (isChecked){
                    mCheckFNBEatery.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckFNBEatery.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckHawkerCentre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.HawkerCentreMarkerList, isChecked);
                if (isChecked){
                    mCheckHawkerCentre.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckHawkerCentre.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckPlayground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.PlaygroundMarkerList, isChecked);
                if (isChecked){
                    mCheckPlayground.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckPlayground.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckShelter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.ShelterMarkerList, isChecked);
                if (isChecked){
                    mCheckShelter.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckShelter.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckToilet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.ToiletMarkerList, isChecked);
                if (isChecked){
                    mCheckToilet.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckToilet.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });

        mCheckWaterCooler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                fullMapViewModel.setMarkerVisibility(fullMapViewModel.WaterCoolerMarkerList, isChecked);
                if (isChecked){
                    mCheckWaterCooler.setTextColor(Color.parseColor("#3361A6"));
                }
                else{
                    mCheckWaterCooler.setTextColor(Color.parseColor("#6C757D"));
                }
            }
        });
    }

    public MutableLiveData<Boolean> getMapReady() {
        AmenitiesMapFragment amenitiesMapFragment = (AmenitiesMapFragment) getChildFragmentManager().getFragments().get(0);
        return amenitiesMapFragment.getMapReady();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}