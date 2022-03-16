package com.example.bikerx.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.LoginActivity;
import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.databinding.FragmentHomeBinding;
import com.example.bikerx.ui.session.ModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeRecommendationsAdapter.MyViewHolder.HomeRouteListener{
    private String TAG = "HOME_FRAGMENT";
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding mBinding;
    private List<ModelClass> routeList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mBinding.getRoot().getContext());
        mBinding.HomeRecyclerView.setLayoutManager(layoutManager);
        routeList = new ArrayList<>();
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Round Island", "5.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Mandai Loop", "5.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Seletar Loop", "3.0"));
        routeList.add(new ModelClass(R.drawable.common_google_signin_btn_icon_dark, "Sentosa Bike Trail", "4.0"));
        mBinding.HomeRecyclerView.setAdapter(new HomeRecommendationsAdapter(routeList, this));
        View root = mBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindButtons();
        displayWeather();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void displayWeather() {
        //to be implemented
    }

    private void bindButtons() {
        mBinding.recommendedRouteSeeAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionNavigationHomeToRecommendationsFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });
        mBinding.viewMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_map);
            }
        });
        mBinding.viewGoalsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionNavigationHomeToGoalsFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });
        mBinding.ownRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        mBinding.HomeRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void homeRouteClick(int position) {
        int p = position;
        Log.i("routeName", "routeName: " + routeList.get(p).getRouteName());
        NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
        Navigation.findNavController(this.getView()).navigate(action);
    }
}