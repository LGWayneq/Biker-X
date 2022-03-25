package com.example.bikerx.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.example.bikerx.R;
import com.example.bikerx.control.WeatherApiService;
import com.example.bikerx.control.WeatherResult;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeViewModel extends ViewModel {

    private WeatherApiService mVolleyService;
    private WeatherResult mResultCallback;
    private MutableLiveData<Drawable> res = new MutableLiveData<Drawable>();

    public MutableLiveData<Drawable> getWeatherData(Context context) {
        mResultCallback = new WeatherResult() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                if (requestType.equals("GET_WEATHER_DATA")) {
                    try {
                        String forecast = response.getJSONArray("items").getJSONObject(0).getJSONArray("forecasts").getJSONObject(12).getString("forecast");
                        Log.d("test", forecast);
                        switch (forecast) {
                            case "Partly Cloudy (Day)":
                                res.setValue(context.getResources().getDrawable(R.drawable.cloudy_day));
                                break;
                            case "Partly Cloudy (Night)":
                                res.setValue(context.getResources().getDrawable(R.drawable.cloudy_night));
                                break;
                            case "Cloudy":
                                res.setValue(context.getResources().getDrawable(R.drawable.cloudy));
                                break;
                            case "Light Showers":
                            case "Showers":
                            case "Moderate Rain":
                                res.setValue(context.getResources().getDrawable(R.drawable.rain));
                                break;
                            case "Thundery Showers":
                            case "Heavy Thundery Showers with Gusty Winds":
                                res.setValue(context.getResources().getDrawable(R.drawable.storm));
                                break;
                            default:
                                res.setValue(context.getResources().getDrawable(R.drawable.sun));
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void notifyError(String requestType, VolleyError error) {

            }

        };
        mVolleyService = new WeatherApiService(mResultCallback, context);
        mVolleyService.getDataVolley("GET_WEATHER_DATA", "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast");
        return res;
    }
}