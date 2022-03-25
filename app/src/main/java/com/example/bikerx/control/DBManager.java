package com.example.bikerx.control;

import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.bikerx.entities.GoalsInfo;
import com.example.bikerx.R;
import com.example.bikerx.ui.chat.ForumThread;
import com.example.bikerx.ui.chat.Message;
import com.example.bikerx.ui.chat.MessageViewModel;
import com.example.bikerx.entities.Goal;
import com.example.bikerx.ui.history.CyclingHistory;
import com.example.bikerx.ui.home.Route1;
import com.example.bikerx.ui.home.Routee;
import com.example.bikerx.ui.session.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.squareup.okhttp.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference;
    public static final String TAG = "DBManager";

    public void addRatings(String routeId, String userId, float rating) {
        db.collection("routes").document(routeId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                List<HashMap<String, Object>> ratings = (List<HashMap<String, Object>>) data.get("ratings");
                for (HashMap<String, Object> user : ratings) {
                    if (user.get("userId").toString().compareTo(userId) == 0) {
                        ratings.remove(user);
                        break;
                    }
                }
                HashMap<String, Object> entry = new HashMap<String, Object>();
                entry.put("userId", userId);
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
                entry.put("date", date);
                entry.put("formattedDistance", formattedDistance);
                entry.put("duration", duration);
                if (data == null) {
                    HashMap<String, List<HashMap<String, Object>>> newUser = new HashMap<String, List<HashMap<String, Object>>>();
                    ArrayList<HashMap<String, Object>> history = new ArrayList<HashMap<String, Object>>();
                    history.add(entry);
                    newUser.put("history", history);
                    db.collection("users").document(userId).set(newUser);
                } else {
                    List<HashMap<String, Object>> history = (List<HashMap<String, Object>>) data.get("history");
                    if (history == null) {
                        history = new ArrayList<HashMap<String, Object>>();
                    }
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

                } else {
                    List<HashMap<String, Object>> historyData = (List<HashMap<String, Object>>) data.get("history");
                    if (historyData == null) {
                        history.setValue(null);
                    }
                    else {
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
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                        Log.d(TAG, "onComplete: threadID "+document.getData().get("threadId").toString());
//                        Log.d(TAG, "onComplete: threadName "+document.getData().get("threadName").toString());
//                        Log.d(TAG, "onComplete: threadMessages "+document.getData().get("messages").toString());

                        String threadId = document.getData().get("threadId").toString();
                        String threadName = document.getData().get("threadName").toString();
                        List<HashMap<String, Object>> messageList = (List<HashMap<String, Object>>) document.getData().get("messages");
                        ArrayList<Message> newMessageArray = new ArrayList<>();
                        HashMap<String, Object> messageIndividual = messageList.get(messageList.size()-1);
                        Message newMessage = new Message(
                                (String) messageIndividual.get("userId"),
                                (String) messageIndividual.get("userName"),
                                (String) messageIndividual.get("messageID"),
                                (Timestamp) messageIndividual.get("time"),
                                ((String) messageIndividual.get("messageContent")).replace("\\n", "\n"));
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
//                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(activity.getApplicationContext(), "Error Getting Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        return forumThreadArrayList;
    }


    public MutableLiveData<ArrayList<Message>> getForumMessage(Activity activity, String threadId) {
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
                                    (Timestamp) forumMessage.get("time"),
                                    ((String) forumMessage.get("messageContent")).replace("\\n", "\n"));
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

    public void addForumMessage(Activity activity, String threadId, String userId, String userName, String messageId, Timestamp time, String messageContent){
        db.collection("forum-threads").document(threadId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> data = task.getResult().getData();
                    List<HashMap<String, Object>> messages = (List<HashMap<String, Object>>) data.get("messages");
                    HashMap<String, Object> entry = new HashMap<String, Object>();
                    entry.put("userId", userId );
                    entry.put("userName", userName);
                    entry.put("messageId", messageId );
                    entry.put("time", time);
                    entry.put("messageContent", messageContent );
                    messages.add(entry);
                    db.collection("forum-threads").document(threadId).update("messages", messages);
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public ArrayList<Route1> getRoute(String routeName) {
        ArrayList<Route1> r = new ArrayList<>();
        db.collection("PCN").whereEqualTo("name", routeName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Route1> inner = new ArrayList<Route1>();
                    for (DocumentSnapshot document : task.getResult()) {
                        inner.add(document.toObject(Route1.class));
                        //r.add(inner);
                    }
                } else {
                    Log.d("getRoute", "Error getting route: ", task.getException());
                }
            }
        });
        return r;
    }

    public MutableLiveData<ArrayList<ModelClass>> getRecommendedRoutes() {
        MutableLiveData<ArrayList<ModelClass>> routeList = new MutableLiveData<>();
        routeList.setValue(new ArrayList<ModelClass>());
        db.collection("routes1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        ModelClass m = new ModelClass(R.drawable.common_google_signin_btn_icon_dark, (String) data.get("name"), "5.0");
                        ArrayList<ModelClass> newArray = routeList.getValue();
                        newArray.add(m);
                        Log.d("db", m.getRouteName());
                        routeList.setValue(newArray);
                    }
                }
            }
        });
        return routeList;

    }


    public MutableLiveData<Goal> getGoal(String userId) {
        MutableLiveData<Goal> goal = new MutableLiveData<Goal>();
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                if (data == null) {
                    goal.setValue(null);
                } else {
                    HashMap<String, Object> goalData = (HashMap<String, Object>) data.get("goals");
                    if (goalData == null) {
                        goal.setValue(null);
                    } else {
                        Goal newGoal = new Goal();
                        if (goalData.get("distance") != null) {
                            newGoal.setDistance((double)((long)goalData.get("distance")));
                        }
                        if (goalData.get("duration") != null) {
                            newGoal.setDuration((long)goalData.get("duration"));
                        }
                        goal.setValue(newGoal);
                    }
                }
            }
        });
        return goal;
    }

    public void setMonthlyDistanceGoal(String userId, int monthlyDistanceInKm) {
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                if (data == null) {
                    // User does not exist in Firestore
                    data = new HashMap<String, Object>();
                    HashMap<String, Object> goals = new HashMap<String, Object>();
                    goals.put("distance", monthlyDistanceInKm);
                    data.put("goals", goals);
                } else {
                    // User exists in Firestore
                    HashMap<String, Object> goals = new HashMap<String, Object>();
                    goals.put("distance", monthlyDistanceInKm);
                    if (data.get("goals") != null) {
                        Map<String, Object> existingGoals = (Map<String, Object>) data.get("goals");
                        if (existingGoals.get("duration") != null) {
                            goals.put("duration", existingGoals.get("duration"));
                        }
                    }
                    data.put("goals", goals);
                }
                db.collection("users").document(userId).set(data);
            }
        });
    }

    public void setMonthlyTimeGoal(String userId, int monthlyTimeInHours) {
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                if (data == null) {
                    // User does not exist in Firestore
                    data = new HashMap<String, Object>();
                    HashMap<String, Object> goals = new HashMap<String, Object>();
                    goals.put("duration", monthlyTimeInHours * 3600 * 1000);
                    data.put("goals", goals);
                } else {
                    HashMap<String, Object> goals = new HashMap<String, Object>();
                    goals.put("duration", monthlyTimeInHours * 3600 * 1000);
                    if (data.get("goals") != null) {
                        Map<String, Object> existingGoals = (Map<String, Object>) data.get("goals");
                        if (existingGoals.get("distance") != null) {
                            goals.put("distance", existingGoals.get("distance"));
                        }
                    }
                    data.put("goals", goals);
                    db.collection("users").document(userId).set(data);
                }
            }
        });
    }
}
