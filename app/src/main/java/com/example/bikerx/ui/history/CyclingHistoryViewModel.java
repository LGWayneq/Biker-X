package com.example.bikerx.ui.history;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.control.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class CyclingHistoryViewModel extends ViewModel {
    private DBManager dbManager = new DBManager();
    private MutableLiveData<ArrayList<CyclingHistory>> cyclingHistory;

    public void fetchCyclingHistory(String userId) {
        cyclingHistory = dbManager.getCyclingHistory(userId);
    }

    public MutableLiveData<ArrayList<CyclingHistory>> getCyclingHistory() {
        return cyclingHistory;
    }

    public MutableLiveData<HashMap<String, Object>> calculateMonthlyData(LifecycleOwner owner) {
        MutableLiveData<HashMap<String, Object>> data = new MutableLiveData<HashMap<String, Object>>();
        data.setValue(new HashMap<String, Object>());
        cyclingHistory.observe(owner, new Observer<ArrayList<CyclingHistory>>() {
            @Override
            public void onChanged(ArrayList<CyclingHistory> cyclingHistory) {
                double monthDistance = 0;
                long monthDuration = 0;
                Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
                for (CyclingHistory entry: cyclingHistory) {
                    String targetDate = dateFormat.format(date);
                    if (targetDate.compareTo(entry.getDate().substring(3,11)) == 0) {
                        monthDistance += Double.parseDouble(entry.getFormattedDistance());
                        monthDuration += entry.getDuration();
                    }
                }
                HashMap<String, Object> hashMap = data.getValue();
                hashMap.put("monthDistance", monthDistance);
                hashMap.put("monthDuration", monthDuration);
                data.setValue(hashMap);
            }
        });
        return data;
    }
}