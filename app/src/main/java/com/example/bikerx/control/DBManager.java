package com.example.bikerx.control;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bikerx.ui.chat.ForumThread;
import com.example.bikerx.ui.chat.Message;
import com.example.bikerx.ui.chat.MessageViewModel;
import com.example.bikerx.ui.history.CyclingHistory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DBManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG = "DBManager";

    public void addRatings(String routeId, String userId, float rating) {
        db.collection("routes").document(routeId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                List<HashMap<String, Object>> ratings = ( List<HashMap<String, Object>>) data.get("ratings");
                for (HashMap<String, Object> user: ratings) {
                    if (user.get("userId").toString().compareTo(userId) == 0){
                        ratings.remove(user);
                        break;
                    }
                }
                HashMap<String, Object> entry = new HashMap<String, Object>();
                entry.put("userId", userId );
                entry.put("rating", rating);
                ratings.add(entry);
                db.collection("routes").document(routeId).update("ratings", ratings);
            }
        });
    }

    public void addCyclingSession(String userId, String date, String formattedDistance, long duration) {
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                HashMap<String, Object> entry = new HashMap<String, Object>();
                entry.put("date", date );
                entry.put("formattedDistance", formattedDistance);
                entry.put("duration", duration);
                if (data == null) {
                    HashMap<String,List<HashMap<String, Object>>> newUser = new HashMap<String,List<HashMap<String, Object>>>();
                    ArrayList<HashMap<String, Object>> history = new ArrayList<HashMap<String, Object>>();
                    history.add(entry);
                    newUser.put("history", history);
                    db.collection("users").document(userId).set(newUser);
                } else {
                    List<HashMap<String, Object>> history = (List<HashMap<String, Object>>) data.get("history");
                    history.add(entry);
                    db.collection("users").document(userId).update("history", history);
                }
            }
        });
    }

    public MutableLiveData<ArrayList<CyclingHistory>> getCyclingHistory(String userId) {
        MutableLiveData<ArrayList<CyclingHistory>> history = new MutableLiveData<ArrayList<CyclingHistory>>();
        history.setValue(new ArrayList<CyclingHistory>());
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                if (data == null) {
                    history.setValue(null);
                } else {
                    List<HashMap<String, Object>> historyData = (List<HashMap<String, Object>>) data.get("history");
                    for (HashMap<String, Object> session: historyData) {
                        CyclingHistory newHistory = new CyclingHistory(
                                (String) session.get("date"),
                                (String) session.get("formattedDistance"),
                                (long) session.get("duration"));
                        ArrayList<CyclingHistory> newHistoryArray = history.getValue();
                        newHistoryArray.add(newHistory);
                        history.setValue(newHistoryArray);
                    }
                }
            }
        });
        return history;
    }

    public MutableLiveData<ArrayList<ForumThread>> getForumThread(Activity activity){
        MutableLiveData<ArrayList<ForumThread>> forumThreadArrayList = new MutableLiveData<ArrayList<ForumThread>>();
        forumThreadArrayList.setValue(new ArrayList<ForumThread>());

        db.collection("forum-threads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Log.d(TAG, "onComplete: threadID "+document.getData().get("threadId").toString());
                        Log.d(TAG, "onComplete: threadName "+document.getData().get("threadName").toString());
                        Log.d(TAG, "onComplete: threadMessages "+document.getData().get("messages").toString());

                        String threadId = document.getData().get("threadId").toString();
                        String threadName = document.getData().get("threadName").toString();
                        List<HashMap<String, Object>> messageList = (List<HashMap<String, Object>>) document.getData().get("messages");
                        ArrayList<Message> newMessageArray = new ArrayList<>();
                        HashMap<String, Object> messageIndividual = messageList.get(messageList.size()-1);
                        Message newMessage = new Message(
                                (String) messageIndividual.get("userId"),
                                (String) messageIndividual.get("userName"),
                                (String) messageIndividual.get("messageID"),
                                (String) messageIndividual.get("time").toString(),
                                (String) messageIndividual.get("messageContent"));
                        newMessageArray.add(newMessage);

                        ForumThread newForumThread = new ForumThread(
                                threadId,
                                threadName,
                                newMessageArray
                        );
                        ArrayList<ForumThread> newForumThreadArray = forumThreadArrayList.getValue();
                        newForumThreadArray.add(newForumThread);
                        forumThreadArrayList.setValue(newForumThreadArray);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(activity.getApplicationContext(), "Error Getting Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        return forumThreadArrayList;
    }


    public MutableLiveData<ArrayList<Message>> getForumMessage(Activity activity, String threadId){
//        For user to refresh the specific forum chat, preferably by swiping down
        MutableLiveData<ArrayList<Message>> forumMessageMutableArray = new MutableLiveData<ArrayList<Message>>();
        forumMessageMutableArray.setValue(new ArrayList<Message>());

        db.collection("forum-threads").document(threadId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> data = task.getResult().getData();
                    if (data == null) {
                        forumMessageMutableArray.setValue(null);
                    } else {
                        List<HashMap<String, Object>> forumMessageList = (List<HashMap<String, Object>>) data.get("messages");
                        for (HashMap<String, Object> forumMessage : forumMessageList) {
                            Message message = new Message(
                                    (String) forumMessage.get("userId"),
                                    (String) forumMessage.get("userName"),
                                    (String) forumMessage.get("messageID"),
                                    (String) forumMessage.get("time").toString(),
                                    (String) forumMessage.get("messageContent"));
                            ArrayList<Message> newForumMessageMutableArray = forumMessageMutableArray.getValue();
                            newForumMessageMutableArray.add(message);
                            forumMessageMutableArray.setValue(newForumMessageMutableArray);
                        }
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return forumMessageMutableArray;
    }

    public void addForumMessage(String threadId, String userId, String userName, String messageID, String time, String messageContent){
        db.collection("forum-threads").document(threadId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                List<HashMap<String, Object>> messages = (List<HashMap<String, Object>>) data.get("messages");
                HashMap<String, Object> entry = new HashMap<String, Object>();
                entry.put("userId", userId );
                entry.put("userName", userName);
                entry.put("messageID", messageID );
                entry.put("time", time);
                entry.put("messageContent", messageContent );
                messages.add(entry);
                db.collection("forum-threads").document(threadId).update("messages", messages);
            }
        });
    }

}
