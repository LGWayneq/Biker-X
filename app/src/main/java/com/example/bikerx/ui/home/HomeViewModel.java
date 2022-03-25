package com.example.bikerx.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;
import com.example.bikerx.ui.history.CyclingHistory;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private DBManager dbManager = new DBManager();
    private MutableLiveData<String> userName = new MutableLiveData<String>();
    private MutableLiveData<ArrayList<Routee>> homeRoutes;


    public void fetchHomeRoutes() {
        homeRoutes = dbManager.getHomeRoutes();
    }

    public MutableLiveData<ArrayList<Routee>> getHomeRoutes() {
        return homeRoutes;
    }

    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }
    public LiveData<String> getUserName() {
        return userName;
    }
}