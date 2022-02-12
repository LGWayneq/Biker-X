package com.example.bikerx.ui.session;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerx.R;
import com.example.bikerx.databinding.SessionSummaryFragmentBinding;

public class SessionSummaryFragment extends Fragment {

    private SessionSummaryViewModel mViewModel;
    private SessionSummaryFragmentBinding mBinding;

    public static SessionSummaryFragment newInstance() {
        return new SessionSummaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SessionSummaryFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long timeElapsed = SessionSummaryFragmentArgs.fromBundle(getArguments()).getTimeElapsed();
        mBinding.chronometer.setBase(timeElapsed);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SessionSummaryViewModel.class);

    }

}