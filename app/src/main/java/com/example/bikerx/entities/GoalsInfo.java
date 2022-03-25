package com.example.bikerx.entities;

public class GoalsInfo {
    private int monthlyDurationInHours;
    private int monthlyDistanceInKm;

    public GoalsInfo(){

    }

    public int getMonthlyDurationInHours() {
        return monthlyDurationInHours;
    }

    public void setMonthlyDurationInHours(int monthlyDurationInHours) {
        this.monthlyDurationInHours = monthlyDurationInHours;
    }

    public int getMonthlyDistanceInKm() {
        return monthlyDistanceInKm;
    }

    public void setMonthlyDistanceInKm(int monthlyDistanceInKm) {
        this.monthlyDistanceInKm = monthlyDistanceInKm;
    }


}
