package com.example.bikerx.ui.session;

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

import com.example.bikerx.databinding.StartCyclingFragmentBinding;


public class CyclingSessionFragment extends Fragment {
    enum SessionState {
        PRE_START,
        STARTED,
        PAUSED,
    }
    private CyclingSessionViewModel mViewModel;
    private StartCyclingFragmentBinding mBinding;
    private Chronometer chronometer;
    private long pausedTime;
    private SessionState state;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = StartCyclingFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindButtons();

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
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        state = SessionState.STARTED;
        mBinding.startButton.setVisibility(View.GONE);
        mBinding.pauseButton.setVisibility(View.VISIBLE);
        mBinding.stopButton.setVisibility(View.VISIBLE);
    }

    private void pauseSession() {
        chronometer.stop();
        state = SessionState.PAUSED;
        pausedTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        mBinding.pauseButton.setVisibility(View.GONE);
        mBinding.resumeButton.setVisibility(View.VISIBLE);
    }

    private void resumeSession() {
        chronometer.setBase(pausedTime + SystemClock.elapsedRealtime());
        chronometer.start();
        state = SessionState.STARTED;
        mBinding.pauseButton.setVisibility(View.VISIBLE);
        mBinding.resumeButton.setVisibility(View.GONE);
    }

    private void stopSession() {
        if (state == SessionState.PAUSED) {
            chronometer.setBase(pausedTime + SystemClock.elapsedRealtime());
        }
        long timeElapsed = chronometer.getBase();
        NavDirections action = CyclingSessionFragmentDirections.actionStartCyclingFragmentToSessionSummaryFragment(timeElapsed);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CyclingSessionViewModel.class);
        // TODO: Use the ViewModel
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