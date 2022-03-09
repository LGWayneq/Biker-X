package com.example.bikerx.ui.session;

import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;

public class SessionSummaryViewModel extends ViewModel {
    DBManager dbManager = new DBManager();

    public void rateRoute(String routeId, String userId, float rating) {
        dbManager.addRatings(routeId, userId, rating);
    }
}