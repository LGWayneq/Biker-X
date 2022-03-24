package com.example.bikerx.ui.chat;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bikerx.R;

import java.util.ArrayList;


public class MessageFragment extends Fragment {
    private static final String TAG = "MessageFragment";
    private MessageViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private View view;
    MessageAdapter mAdapter;
    private String threadId = "fsMgD4ddjRdK1bXLr0Dp";
    private String forumHeading = "Cycling Marathon";
    private TextView messageTextbox;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: savedInstanceState = " + savedInstanceState);
//        Log.d(TAG, "onCreate: extracted value = " + savedInstanceState.getString("threadId"));
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        View view = inflater.inflate(R.layout.message_fragment, container, false);

//        Log.d(TAG, "onCreateView: savedInstanceState = " + savedInstanceState);
//        Log.d(TAG, "onCreateView: extracted value = " + savedInstanceState.getString("threadId"));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setForumTitle(view, forumHeading);
        displayMessageList(view);
        bindButtons(view);
    }

    private void setForumTitle(View view, String forumHeading) {
        TextView messageForumHeading = view.findViewById(R.id.messageForumHeading);
        messageForumHeading.setText(forumHeading);
    }

    private void displayMessageList(View view) {
        mViewModel.fetchMessages(getActivity(), threadId);
        mViewModel.getMessages(getActivity()).observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messageArray) {
                mRecyclerView = view.findViewById(R.id.messageRecycleView);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
//                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new MessageAdapter(messageArray);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void bindButtons(View view) {
        messageTextbox = view.findViewById(R.id.sendMessageTextbox);

        view.findViewById(R.id.sendMessageButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String messageContent = messageTextbox.getText().toString();
                mViewModel.sendMessage(getActivity(), threadId, messageContent, mAdapter);
                messageTextbox.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
