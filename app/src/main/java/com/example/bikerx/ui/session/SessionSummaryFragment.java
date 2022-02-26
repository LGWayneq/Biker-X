package com.example.bikerx.ui.session;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerx.R;
import com.example.bikerx.databinding.SessionSummaryFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SessionSummaryFragment extends Fragment {

    private SessionSummaryViewModel mViewModel;
    private SessionSummaryFragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SessionSummaryFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindData();
    }

    private void bindData() {
        long timeElapsed = SessionSummaryFragmentArgs.fromBundle(getArguments()).getTimeElapsed();
        String formattedDistance = SessionSummaryFragmentArgs.fromBundle(getArguments()).getDistanceTravelled();
        mBinding.chronometer.setBase(SystemClock.elapsedRealtime() - timeElapsed);
        mBinding.distanceDetailsFloat.setText(formattedDistance);
        mBinding.avgSpeedFloat.setText(String.format("%.2f", 1000*Float.parseFloat(formattedDistance)/timeElapsed));
        Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault());
        mBinding.dateTextView.setText(dateFormat.format(date));
    }

}