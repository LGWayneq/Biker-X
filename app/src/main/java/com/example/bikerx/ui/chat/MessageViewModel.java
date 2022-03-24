package com.example.bikerx.ui.chat;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.DBManager;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessageViewModel extends ViewModel {
    DBManager dbManager = new DBManager();
    public MutableLiveData<ArrayList<Message>> mutableMessageArrayList;
    private String TAG = "MessageViewModel";

    public void fetchMessages(FragmentActivity activity, String threadId) {
        mutableMessageArrayList = dbManager.getForumMessage(activity, threadId);
    }

    public MutableLiveData<ArrayList<Message>> getMessages(FragmentActivity activity) {
        return mutableMessageArrayList;
    }

    public void sendMessage(FragmentActivity activity, String threadId, String messageContent, MessageAdapter mAdapter) {
        String userName = ((MainActivity) activity).getSupportActionBar().getTitle().toString().replace("Hello, ", "").replace("!","");
        String messageId = threadId + Integer.toString(mutableMessageArrayList.getValue().size());
        Timestamp currentTimestamp = Timestamp.now();

        dbManager.addForumMessage(threadId, ((MainActivity)activity).getUserId(), userName, messageId, currentTimestamp, messageContent);

        Message addedMessage = new Message(((MainActivity)activity).getUserId(), userName, messageId, currentTimestamp, messageContent);
        ArrayList<Message> messageArrayList = mutableMessageArrayList.getValue();
        messageArrayList.add(addedMessage);
        mutableMessageArrayList.setValue(messageArrayList);
        mAdapter.notifyDataSetChanged();
    }
}