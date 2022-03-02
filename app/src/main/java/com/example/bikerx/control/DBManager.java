package com.example.bikerx.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bikerx.ui.history.CyclingHistory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class DBManager {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public void getCyclingHistory(String userId) {
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("test"," inside");
            }
        });
        Log.d("test", "outside");
    }
}
