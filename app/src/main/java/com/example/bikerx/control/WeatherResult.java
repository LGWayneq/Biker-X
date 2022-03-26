package com.example.bikerx.control;

import android.graphics.drawable.Drawable;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface WeatherResult {
    public void notifySuccess(String requestType, JSONObject response);
    public void notifyError(String requestType, VolleyError error);
}
