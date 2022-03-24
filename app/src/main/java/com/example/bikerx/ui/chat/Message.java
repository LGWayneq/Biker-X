package com.example.bikerx.ui.chat;

import com.google.firebase.Timestamp;

public class Message {
    private String userId;
    private String userName;
    private String messageID;
    private Timestamp time;
    private String messageContent;

    public Message(String userId, String userName, String messageID, Timestamp time, String messageContent) {
        this.userId = userId;
        this.userName = userName;
        this.messageID = messageID;
        this.time = time;
        this.messageContent = messageContent;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }

    public Timestamp getTime() { return time; }
    public void setTimeD(Timestamp time) { this.time = time; }

    public String getMessageContent() { return messageContent; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }

}
