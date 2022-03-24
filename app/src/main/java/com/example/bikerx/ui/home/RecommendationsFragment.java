package com.example.bikerx.ui.home;

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
import com.example.bikerx.ui.session.ModelClass;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsFragment extends Fragment implements RecommendationsAdapter.ViewHolder.OnRouteListener {
    private RecyclerView recyclerView;
    private List<ModelClass> routeList;
    private RecommendationsViewModel mViewModel;
    private Button button;
    private DBManager dbManager;

    public static RecommendationsFragment newInstance() {
        return new RecommendationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.dbManager = new DBManager();
        routeList = new ArrayList<>();
        routeList = dbManager.getRecommendedRoutes();

/*        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Round Island", "5.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Mandai Loop", "5.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Seletar Loop", "3.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Sentosa Bike Trail", "4.0"));*/

        View view = inflater.inflate(R.layout.recommendations_fragment, container, false);
        recyclerView = view.findViewById(R.id.recommendationsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecommendationsAdapter(routeList, this));

        /*FirebaseRecyclerOptions.Builder<ModelClass> options =
                new FirebaseRecyclerOptions,Bu
        routeList = new ArrayList<>();
        ModelClass ob1 = new ModelClass(R.drawable.common_full_open_on_phone, "Round Island");
        routeList.add(ob1);
        ModelClass ob2 = new ModelClass(R.drawable.rounded_search_view, "Route 2");
        routeList.add(ob2);
        ModelClass ob3 = new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Route 3");
        routeList.add(ob3);

        recyclerView.setAdapter(new Adapter(routeList)); */

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
        Log.i("routeName", "routeName: " + routeList.get(p).getRouteName());
        NavDirections action = RecommendationsFragmentDirections.actionRecommendationsFragmentToStartCyclingFragment();
        Navigation.findNavController(this.getView()).navigate(action);
    }
}