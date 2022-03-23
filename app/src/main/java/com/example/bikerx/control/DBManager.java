package com.example.bikerx.control;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bikerx.entities.GoalsInfo;
import com.example.bikerx.ui.chat.ForumThread;
import com.example.bikerx.ui.chat.Message;
import com.example.bikerx.entities.Goal;
import com.example.bikerx.ui.history.CyclingHistory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                    history.add(entry);
                    db.collection("users").document(userId).update("history", history);
                }
            }
        });
    }

    public ArrayList<ForumThread> getForumThread(Activity activity) {
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
    public MutableLiveData<ArrayList<Message>> getForumMessage(Activity activity, String threadId) {
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

    public void addForumMessage(String threadId, String userId, String userName, String messageID, String time, String messageContent) {
        db.collection("forum-threads").document(threadId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                List<HashMap<String, Object>> messages = (List<HashMap<String, Object>>) data.get("messages");
                HashMap<String, Object> entry = new HashMap<String, Object>();
                entry.put("userId", userId);
                entry.put("userName", userName);
                entry.put("messageID", messageID);
                entry.put("time", time);
                entry.put("messageContent", messageContent);
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
                    goals.put("duration", monthlyTimeInHours * 3600);
                    data.put("goals", goals);
                } else {
                    HashMap<String, Object> goals = new HashMap<String, Object>();
                    goals.put("duration", monthlyTimeInHours * 3600);
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
