package com.example.bikerx.ui.fullmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.databinding.FragmentFullMapBinding;

public class FullMapFragment extends Fragment {

    private FullMapViewModel fullMapViewModel;
    private FragmentFullMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fullMapViewModel =
                new ViewModelProvider(this).get(FullMapViewModel.class);

        binding = FragmentFullMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}