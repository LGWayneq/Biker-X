package com.example.bikerx.ui.home;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bikerx.R;
import com.example.bikerx.control.ApiManager;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.ui.session.CyclingSessionFragment;
import com.example.bikerx.ui.session.ModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsFragment extends Fragment implements RecommendationsAdapter.ViewHolder.OnRouteListener {
    private RecyclerView recyclerView;
    private ArrayList<Routee> rou;
    private RecommendationsAdapter recommendationsAdapter;
    private FirebaseFirestore db;
    private RecommendationsViewModel mViewModel;
    private Button button;
    private DBManager dbManager;




    public static RecommendationsFragment newInstance() {
        return new RecommendationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        rou = new ArrayList<Routee>();
        View view = inflater.inflate(R.layout.recommendations_fragment, container, false);
        recyclerView = view.findViewById(R.id.recommendationsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recommendationsAdapter = new RecommendationsAdapter(rou, this);
        recyclerView.setAdapter(recommendationsAdapter);
        getRecommendedRoutes();

        button = (Button) view.findViewById(R.id.ownRouteButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RecommendationsFragmentDirections.actionRecommendationsFragmentToStartCyclingFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onRouteClick(int position) {
        int p = position;
        Bundle bundle = new Bundle();
        Log.d("route", rou.get(position).getName());
//        bundle.putString("routename", rou.get(position).getName());
//        CyclingSessionFragment fragment = new CyclingSessionFragment();
//        fragment.setArguments(bundle);
//        FragmentManager manager = getChildFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id., fragment).commit();
        NavDirections action = RecommendationsFragmentDirections.actionRecommendationsFragmentToStartCyclingFragment();
        Navigation.findNavController(this.getView()).navigate(action);
    }

    private void getRecommendedRoutes(){
        db.collection("routes1").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!= null){
                            Log.e("firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                rou.add(dc.getDocument().toObject(Routee.class));

                            }
                            recommendationsAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}