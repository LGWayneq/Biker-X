package com.example.bikerx.ui.chat;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bikerx.R;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.ui.home.HomeFragmentDirections;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    private static final String TAG = "MessageFragment";
    private MessageViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private View view;
    private String threadId = "fsMgD4ddjRdK1bXLr0Dp";
    private String forumHeading = "Cycling Marathon";
    private TextView messageTextbox;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setForumTitle(view, forumHeading);
        displayMessageList(view);
        bindButtons(view);

//        cViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
//        forumThreadList = cViewModel.fetchForumThread();

//        TextView testingView = (TextView) view.findViewById(R.id.forumHeading);
//        String holder = "";
//        for (ForumThread forumThread: forumThreadList) {
//            holder += forumThread.getThreadId() + "\n";
//            holder += forumThread.getThreadName() + "\n\t\t";
//            for (Message message: forumThread.getMessageArrayList()){
//                holder += message.getUserId() + "\n\t\t";
//                holder += message.getUserName() + "\n\t\t";
//                holder += message.getMessageID() + "\n\t\t";
//                holder += message.getTime() + "\n\t\t";
//                holder += message.getMessageContent() + "\n\t\t";
//            }
//            holder += "\n";
//        }
//        testingView.setText(holder);

//        bindButtons();
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
                mRecyclerView.setAdapter(new MessageAdapter(messageArray));
            }
        });
    }

    private void bindButtons(View view) {
        messageTextbox = view.findViewById(R.id.sendMessageTextbox);

        view.findViewById(R.id.sendMessageButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String messageContent = messageTextbox.getText().toString();
                mViewModel.sendMessage(getActivity(), threadId, messageContent);
                messageTextbox.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
