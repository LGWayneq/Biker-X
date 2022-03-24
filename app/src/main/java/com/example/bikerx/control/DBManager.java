package com.example.bikerx.control;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bikerx.R;
import com.example.bikerx.ui.chat.ForumThread;
import com.example.bikerx.ui.chat.Message;
import com.example.bikerx.ui.history.CyclingHistory;
import com.example.bikerx.ui.home.Route1;
import com.example.bikerx.ui.session.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.squareup.okhttp.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DBManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public ArrayList<ForumThread> getForumThread(Activity activity){
        ArrayList<ForumThread> forumThreadArrayList = new ArrayList<>();

//        May or may not be efficient since we pull ALL forum data? Inefficient if forum data is
//        excessive, efficient since it "pre-loads" the data
        db.collection("forum-threads").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list of objects directly!
                            // No need to fetch each document.
                            //List<ForumThread> types = documentSnapshots.getDocuments().get()
                                    //.toObjects(ForumThread.class);

                            // Add all to your list
                            //forumThreadArrayList.addAll(types);
                        }
                    }
                });
//            .addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(activity.getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
//                }
//            });

        return forumThreadArrayList;
//        MutableLiveData<ArrayList<ForumThread>> history = new MutableLiveData<ArrayList<ForumThread>>();
//        history.setValue(new ArrayList<ForumThread>());
//        db.collection("forum-threads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                Map<String, Object> data = task.getResult();
//                if (data == null) {
//                    history.setValue(null);
//                } else {
//                    List<HashMap<String, Object>> historyData = (List<HashMap<String, Object>>) data.get("history");
//                    for (HashMap<String, Object> session: historyData) {
//                        CyclingHistory newHistory = new CyclingHistory(
//                                (String) session.get("date"),
//                                (String) session.get("formattedDistance"),
//                                (long) session.get("duration"));
//                        ArrayList<CyclingHistory> newHistoryArray = history.getValue();
//                        newHistoryArray.add(newHistory);
//                        history.setValue(newHistoryArray);
//                    }
//                }
//            }
//        });
//        return history;

    }


    //    Still not quite sure when to use mutablelivedata vs normal arraylist
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
                                    (String) forumMessage.get("time"),
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
                    for (HashMap<String, Object> session : historyData) {
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



    public Route1 getRoute(String routeName) {
        ArrayList<Route1> r = new ArrayList<>();
        db.collection("PCN").whereEqualTo("name", routeName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult()) {
                        r.add(document.toObject(Route1.class));
                        Log.d("test", r.get(0).getName());
                    }
                } else {
                    Log.d("getRoute", "Error getting route: ", task.getException());
                }
            }
        });
        return r.get(0);}

    public ArrayList<ModelClass> getRecommendedRoutes() {
        ArrayList<ModelClass> routeList = new ArrayList<>();
        db.collection("routes1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        ModelClass m = new ModelClass(R.drawable.common_google_signin_btn_icon_dark, (String) data.get("name"), "5.0");
                        Log.d("db", m.getRouteName());
                        routeList.add(m);
                    }
                }
            }
        });
        Log.d("db", String.valueOf(routeList.size()));
        return routeList;

    }


}
