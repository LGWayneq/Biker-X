package com.example.bikerx.ui.home;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.android.volley.VolleyError;
import com.example.bikerx.IResult;
import com.example.bikerx.R;
import com.example.bikerx.VolleyService;
import com.example.bikerx.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private String TAG = "HOME_FRAGMENT";
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding mBinding;
    private TextView weatherText;
    private VolleyService mVolleyService;
    private IResult mResultCallback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        mResultCallback = new IResult() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                if (requestType.equals("GET_WEATHER_DATA")) {
                    Log.d("HomeFragment", response.toString());
                    try {
                        String forecast = response.getJSONArray("items").getJSONObject(0).getJSONArray("forecasts").getJSONObject(12).getString("forecast");
                        Drawable res;
                        switch (forecast) {
                            case "Partly Cloudy (Day)":
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/cloudy_day",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                            case "Partly Cloudy (Night)":
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/cloudy_night",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                            case "Cloudy":
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/cloudy",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                            case "Light Showers":
                            case "Showers":
                            case "Moderate Rain":
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/rain",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                            case "Thundery Showers":
                            case "Heavy Thundery Showers with Gusty Winds":
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/storm",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                            default:
                                res = getResources().getDrawable(getResources().getIdentifier(
                                        "@drawable/sun",
                                        null,
                                        getActivity().getPackageName()
                                ));
                                mBinding.imageWeather.setImageDrawable(res);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
        mVolleyService = new VolleyService(mResultCallback, getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindButtons();
        getWeatherData();
//        displayWeather();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void getWeatherData() {
        mVolleyService.getDataVolley("GET_WEATHER_DATA", "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast");


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
    }
}