package com.example.bikerx.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessageViewModel extends ViewModel {
    DBManager dbManager = new DBManager();
    public MutableLiveData<ArrayList<Message>> messageArrayList;

    public void fetchMessages(FragmentActivity activity, String threadId) {
        messageArrayList = dbManager.getForumMessage(activity, threadId);
    }

    public MutableLiveData<ArrayList<Message>> getMessages(FragmentActivity activity) {
        return messageArrayList;
    }

    public void sendMessage() {
//        Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault());
//        dbManager.addForumMessage(((MainActivity)activity).getUserId(), dateFormat.format(date), session.getFormattedDistance(), duration);

    }
}