package com.example.bikerx.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.control.ApiManager;
import com.example.bikerx.databinding.SessionSummaryFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SessionSummaryFragment extends Fragment {
    private long totalTime;
    private String totalDistanceFormatted;
    private String routeId;
    private SessionSummaryViewModel mViewModel;
    private SessionSummaryFragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SessionSummaryViewModel.class);
        mBinding = SessionSummaryFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
        bindRatingBar();
    }

    private void bindData() {
        routeId = SessionSummaryFragmentArgs.fromBundle(getArguments()).getRouteId();
        totalDistanceFormatted = SessionSummaryFragmentArgs.fromBundle(getArguments()).getDistanceTravelled();
        totalTime = SessionSummaryFragmentArgs.fromBundle(getArguments()).getTimeElapsed();
        mBinding.chronometer.setBase(SystemClock.elapsedRealtime() - totalTime);
        mBinding.distanceDetailsFloat.setText(totalDistanceFormatted);
        mBinding.avgSpeedFloat.setText(String.format("%.2f", 60000*Float.parseFloat(totalDistanceFormatted)/totalTime));
        Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault());
        mBinding.dateTextView.setText(dateFormat.format(date));
    }

    private void bindRatingBar() {
        if (routeId != null) {
            mBinding.rateRouteRatingBar.setVisibility(View.VISIBLE);
            mBinding.rateRouteTextView.setVisibility(View.VISIBLE);
            mBinding.rateRouteRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) rateRoute(rating);
                }
            });
        }
    }

    private void rateRoute(float rating){
        mViewModel.rateRoute(routeId, ((MainActivity) getActivity()).getUserId() ,rating);
    }

}