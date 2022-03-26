package com.example.bikerx.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;

import java.util.ArrayList;

public class RecommendationsViewModel extends ViewModel {
    private DBManager dbManager = new DBManager();
    private MutableLiveData<ArrayList<Route>> routes;

    public void fetchRoutes() {
        routes = dbManager.getHomeRoutes();
    }

    public MutableLiveData<ArrayList<Route>> getRoutes() {
        return routes;
    }
}