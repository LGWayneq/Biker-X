package com.example.bikerx.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.DBManager;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    DBManager dbManager = new DBManager();
    public MutableLiveData<ArrayList<ForumThread>> forumThreadArrayList;

    public void fetchForumThread(FragmentActivity activity) {
        forumThreadArrayList = dbManager.getForumThread(activity);
    }

    public MutableLiveData<ArrayList<ForumThread>> getForumThread(FragmentActivity activity) {
        return forumThreadArrayList;
    }
}