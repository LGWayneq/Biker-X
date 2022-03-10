package com.example.bikerx.ui.chat;

import java.util.ArrayList;

public class ForumThread {
    private String threadId;
    private String threadName;
    private ArrayList<Message> messageArrayList;

    public ForumThread(String threadId, String threadName, ArrayList<Message> messageArrayList) {
        this.threadId = threadId;
        this.threadName = threadName;
        this.messageArrayList = messageArrayList;
    }

    public String getThreadId() { return threadId; }
    public void setThreadId(String threadId) { this.threadId = threadId; }

    public String getThreadName() { return threadName; }
    public void setThreadName(String threadName) { this.threadName = threadName; }

    public ArrayList<Message> getMessageArrayList() { return messageArrayList; }
    public void setMessageArrayList(ArrayList<Message> messageArrayList) { this.messageArrayList = messageArrayList; }

}
