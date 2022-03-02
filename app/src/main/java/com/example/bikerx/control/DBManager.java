package com.example.bikerx.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }); }
}
