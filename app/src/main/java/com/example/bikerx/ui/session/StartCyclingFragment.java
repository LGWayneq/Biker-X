package com.example.bikerx.ui.session;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.example.bikerx.R;
import com.example.bikerx.databinding.StartCyclingFragmentBinding;
import com.example.bikerx.map.MapFragment;
import com.example.bikerx.ui.home.HomeFragmentDirections;


public class StartCyclingFragment extends Fragment {
    enum State {
        PRE_START,
        STARTED,
        PAUSED,
    }
    private StartCyclingViewModel mViewModel;
    private StartCyclingFragmentBinding mBinding;
    private Chronometer chronometer;
    private long pausedTime;
    private State state;

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
        state = State.PRE_START;
        mBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                state = State.STARTED;
                mBinding.startButton.setVisibility(View.GONE);
                mBinding.pauseButton.setVisibility(View.VISIBLE);
                mBinding.stopButton.setVisibility(View.VISIBLE);
            }
        });
        mBinding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                state = State.PAUSED;
                pausedTime = chronometer.getBase() - SystemClock.elapsedRealtime();
                mBinding.pauseButton.setVisibility(View.GONE);
                mBinding.resumeButton.setVisibility(View.VISIBLE);
            }
        });
        mBinding.resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(pausedTime + SystemClock.elapsedRealtime());
                chronometer.start();
                state = State.STARTED;
                mBinding.pauseButton.setVisibility(View.VISIBLE);
                mBinding.resumeButton.setVisibility(View.GONE);
            }
        });
        mBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == State.PAUSED) {
                    chronometer.setBase(pausedTime + SystemClock.elapsedRealtime());
                }
                long timeElapsed = chronometer.getBase();
                NavDirections action = StartCyclingFragmentDirections.actionStartCyclingFragmentToSessionSummaryFragment(timeElapsed);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StartCyclingViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NavHostFragment.findNavController(this).navigateUp();

        Log.d("test","destreoyed");
    }
}