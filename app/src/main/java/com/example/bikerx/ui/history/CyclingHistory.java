package com.example.bikerx.ui.history;

public class CyclingHistory {
    private String date;
    private String formattedDistance;
    private long duration;

    public CyclingHistory(String date, String formattedDistance, long duration) {
        this.date = date;
        this.formattedDistance = formattedDistance;
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormattedDistance() {
        return formattedDistance;
    }

    public void setFormattedDistance(String formattedDistance) {
        this.formattedDistance = formattedDistance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
