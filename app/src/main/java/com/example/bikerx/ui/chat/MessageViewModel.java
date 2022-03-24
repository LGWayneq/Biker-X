package com.example.bikerx.ui.chat;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bikerx.MainActivity;
import com.example.bikerx.control.DBManager;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class MessageViewModel extends ViewModel {
    DBManager dbManager = new DBManager();
    public MutableLiveData<ArrayList<Message>> mutableMessageArrayList;

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

        dbManager.addForumMessage(activity, threadId, ((MainActivity)activity).getUserId(), userName, messageId, currentTimestamp, messageContent);

        Message addedMessage = new Message(((MainActivity)activity).getUserId(), userName, messageId, currentTimestamp, messageContent);
        ArrayList<Message> messageArrayList = mutableMessageArrayList.getValue();
        messageArrayList.add(addedMessage);
        mutableMessageArrayList.setValue(messageArrayList);
    }
}