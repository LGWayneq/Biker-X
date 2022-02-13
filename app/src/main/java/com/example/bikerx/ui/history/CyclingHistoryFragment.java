package com.example.bikerx.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.databinding.FragmentHistoryBinding;

public class CyclingHistoryFragment extends Fragment {

    private CyclingHistoryViewModel cyclingHistoryViewModel;
    private FragmentHistoryBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cyclingHistoryViewModel =
                new ViewModelProvider(this).get(CyclingHistoryViewModel.class);

        mBinding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}