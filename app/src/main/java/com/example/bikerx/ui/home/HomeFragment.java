package com.example.bikerx.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;
import com.example.bikerx.databinding.FragmentHomeBinding;
import com.example.bikerx.ui.session.ModelClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays UI for the app landing page.
 * Contains weather data, recommended routes, and navigation buttons to other app features.
 */
public class HomeFragment extends Fragment implements HomeRecommendationsAdapter.MyViewHolder.HomeRouteListener{
    private String TAG = "HOME_FRAGMENT";
    private FragmentHomeBinding mBinding;
    private List<ModelClass> routeList;
    private HomeViewModel viewModel;

    /**Initialises HomeFragment. The HomeViewModel and FragmentHomeBinding is instantiated here.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

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

    /**Initiates behaviour required of HomeFragment. This method is called after onCreateView.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindButtons();
        displayWeather();

    }

    /**
     * Displays weather data using data fetched from viewModel.
     */
    private void displayWeather() {
        viewModel.getWeatherData(getContext()).observe(this, new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                mBinding.imageWeather.setImageDrawable(drawable);
            }
        });

    }

    /**
     * This method sets the logic of the buttons in the UI.
     */
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