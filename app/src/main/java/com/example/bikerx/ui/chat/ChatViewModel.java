package com.example.bikerx.ui.chat;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.control.LocationManager;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    DBManager dbManager = new DBManager();
    private AppCompatActivity activity;
    public ArrayList<ForumThread> forumThreadArrayList;

//    public ChatViewModel(Context context, AppCompatActivity activity) {
//        this.activity = activity;
//    }



    public ArrayList<ForumThread> fetchForumThread() {
        forumThreadArrayList = dbManager.getForumThread((MainActivity)activity);
        return forumThreadArrayList;
    }

//    private MutableLiveData<String> userName = new MutableLiveData<String>();
//
//    public void setUserName(String userName) {
//        this.userName.setValue(userName);
//    }
//    public LiveData<String> getUserName() {
//        return userName;
//    }
}