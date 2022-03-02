package com.example.bikerx.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;

public class CyclingHistoryViewModel extends ViewModel {
    private DBManager dbManager = new DBManager();

    public void getCyclingHistory(String userId) {
        dbManager.getCyclingHistory(userId);
    }
}