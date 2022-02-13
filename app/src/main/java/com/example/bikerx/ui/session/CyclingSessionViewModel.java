package com.example.bikerx.ui.session;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CyclingSessionViewModel extends ViewModel {
    private MutableLiveData<Float> distance;

    private void initialiseSession() {
        distance = new MutableLiveData<Float>(0.0F);
    }

    private MutableLiveData<Float> getDistance() {
        return distance;
    }
}