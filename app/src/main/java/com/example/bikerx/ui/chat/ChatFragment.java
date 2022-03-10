package com.example.bikerx.ui.chat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerx.R;
import com.example.bikerx.databinding.FragmentHomeBinding;
import com.example.bikerx.ui.home.HomeFragmentDirections;
import com.example.bikerx.ui.home.HomeViewModel;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = mBinding.getRoot();
//
//        return root;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        bindButtons();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

//    private void bindButtons() {
//        mBinding.recommendedRouteSeeAll.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToRecommendationsFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//        mBinding.viewMapButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.navigation_map);
//            }
//        });
//        mBinding.viewGoalsButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToGoalsFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//        mBinding.ownRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//    }

}
//
//public class HomeFragment extends Fragment {
//    private String TAG = "HOME_FRAGMENT";
//    private HomeViewModel homeViewModel;
//    private FragmentHomeBinding mBinding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = mBinding.getRoot();
//
//        return root;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        bindButtons();
//        displayWeather();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mBinding = null;
//    }
//
//    private void displayWeather() {
//        //to be implemented
//    }
//
//    private void bindButtons() {
//        mBinding.recommendedRouteSeeAll.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToRecommendationsFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//        mBinding.viewMapButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.navigation_map);
//            }
//        });
//        mBinding.viewGoalsButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToGoalsFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//        mBinding.ownRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//    }
