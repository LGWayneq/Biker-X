package com.example.bikerx.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> userName = new MutableLiveData<String>();

    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }
    public LiveData<String> getUserName() {
        return userName;
    }
}