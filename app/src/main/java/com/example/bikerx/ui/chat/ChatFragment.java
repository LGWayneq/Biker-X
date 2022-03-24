package com.example.bikerx.ui.chat;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerx.R;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ChatViewModel cViewModel;
    private RecyclerView cRecyclerView;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayForumThread(view);
    }

    private void displayForumThread(View view) {
        cViewModel.fetchForumThread(getActivity());
        cViewModel.getForumThread(getActivity()).observe(this, new Observer<ArrayList<ForumThread>>() {
            @Override
            public void onChanged(ArrayList<ForumThread> forumThreadArray) {
                cRecyclerView = view.findViewById(R.id.forumThreadRecycleView);
                cRecyclerView.setHasFixedSize(true);
                LinearLayoutManager cLayoutManager = new LinearLayoutManager(view.getContext());
                cRecyclerView.setLayoutManager(cLayoutManager);
                cRecyclerView.setAdapter(new ForumThreadAdapter(forumThreadArray));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
