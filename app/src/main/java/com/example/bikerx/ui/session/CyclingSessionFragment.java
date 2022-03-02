package com.example.bikerx.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.example.bikerx.databinding.CyclingSessionFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class CyclingSessionFragment extends Fragment {
    private CyclingSessionViewModel viewModel;
    private CyclingSessionFragmentBinding mBinding;
    private Chronometer chronometer;
    private long pausedTimeElapsed = 0;
    private SessionState state;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = CyclingSessionFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new CyclingSessionViewModelFactory(requireContext())).get(CyclingSessionViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindButtons();
        bindData();
    }

    private void bindData() {
        Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault());
        mBinding.dateTextView.setText(dateFormat.format(date));
        viewModel.getSession().observe(this, new Observer<Session>() {
            @Override
            public void onChanged(Session session) {
                float distance = Float.parseFloat(session.getFormattedDistance());
                float timeElapsed = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                float speed = timeElapsed == 0 ? 0 : 60*distance/timeElapsed;
                String formattedSpeed = String.format("%.2f", speed);
                mBinding.distanceDetailsFloat.setText(session.getFormattedDistance());
                if (state != SessionState.PAUSED) mBinding.avgSpeedFloat.setText(formattedSpeed);
            }
        });
    }

    private void bindButtons() {
        chronometer = mBinding.chronometer;
        state = SessionState.PRE_START;
        mBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSession();
            }
        });
        mBinding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseSession();
            }
        });
        mBinding.resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeSession();
            }
        });
        mBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSession();
            }
        });
    }

    private void startSession() {
        viewModel.initialiseSession((AppCompatActivity) getActivity());
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        state = SessionState.STARTED;
        mBinding.startButton.setVisibility(View.GONE);
        mBinding.pauseButton.setVisibility(View.VISIBLE);
        mBinding.stopButton.setVisibility(View.VISIBLE);
    }

    private void pauseSession() {
        viewModel.pauseTracking();
        chronometer.stop();
        state = SessionState.PAUSED;
        pausedTimeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        mBinding.pauseButton.setVisibility(View.GONE);
        mBinding.resumeButton.setVisibility(View.VISIBLE);
    }

    private void resumeSession() {
        viewModel.resumeTracking();
        chronometer.setBase(SystemClock.elapsedRealtime() - pausedTimeElapsed);
        chronometer.start();
        state = SessionState.STARTED;
        mBinding.pauseButton.setVisibility(View.VISIBLE);
        mBinding.resumeButton.setVisibility(View.GONE);
    }

    private void stopSession() {
        if (state == SessionState.PAUSED) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pausedTimeElapsed);
        }
        String formattedDistance = viewModel.getSession().getValue().getFormattedDistance();
        long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        viewModel.stopTracking();
        NavDirections action = CyclingSessionFragmentDirections
                .actionStartCyclingFragmentToSessionSummaryFragment(formattedDistance, timeElapsed, "NlYqwYPR5GHIJqROvXpp");
        //ROUTEID CURRENTLY HARDCODED RMB TO CHANGE
        NavHostFragment.findNavController(this).navigate(action);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //NavHostFragment.findNavController(this).navigateUp();
    }
}