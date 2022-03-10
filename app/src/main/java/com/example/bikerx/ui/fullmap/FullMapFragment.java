//IN PROGRESS
package com.example.bikerx.ui.fullmap;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.R;

import com.example.bikerx.databinding.FragmentFullMapBinding;

public class FullMapFragment extends Fragment {

    private FullMapViewModel fullMapViewModel;
    private FragmentFullMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fullMapViewModel = new ViewModelProvider(this).get(FullMapViewModel.class);

        binding = FragmentFullMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

    }
 /*   private class ListItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Facility facility = mAdapter.getItem(position);
            Log.d("OnItemClick","In BriefViewFragment");
            ((MainActivity)getActivity()).enterCompleteView(facility);
        }
    } */


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


