package com.example.bikerx.ui.goals;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;
import com.example.bikerx.entities.Goal;
import com.example.bikerx.entities.GoalsInfo;

public class GoalsViewModel extends ViewModel {
    private DBManager dbManager = new DBManager();
    private MutableLiveData<Goal> goals;

    public void fetchGoals(String userId) {
        goals = dbManager.getGoal(userId);
    }

    public MutableLiveData<Goal> getGoals() {
        return goals;
    }

    public void updateDistanceGoal(String userId, int monthlyDistanceInKm){
        dbManager.setMonthlyDistanceGoal(userId, monthlyDistanceInKm);
    }
    public void updateTimeGoal(String userId, int monthlyTimeInHours){
        dbManager.setMonthlyTimeGoal(userId, monthlyTimeInHours);
    }
}
