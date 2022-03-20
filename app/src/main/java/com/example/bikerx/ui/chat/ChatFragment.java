package com.example.bikerx.ui.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.control.DBManager;
import com.example.bikerx.databinding.FragmentHistoryBinding;
import com.example.bikerx.databinding.FragmentHomeBinding;
import com.example.bikerx.ui.history.CyclingHistory;
import com.example.bikerx.ui.history.CyclingHistoryAdapter;
import com.example.bikerx.ui.history.CyclingHistoryViewModel;
import com.example.bikerx.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    private ChatViewModel cViewModel;
    private RecyclerView cRecyclerView;
    private View view;

    DBManager dbManager = new DBManager();

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

    private void displayForumThread(View view) {
        cViewModel.fetchForumThread();
        cViewModel.getForumThread().observe(this, new Observer<ArrayList<ForumThread>>() {
            @Override
            public void onChanged(ArrayList<ForumThread> forumThreadArray) {
                cRecyclerView = view.findViewById(R.id.forumThreadRecycleView);
                cRecyclerView.setHasFixedSize(true);
                LinearLayoutManager cLayoutManager = new LinearLayoutManager(view.getContext());
//                cLayoutManager.setReverseLayout(true);
//                cLayoutManager.setStackFromEnd(true);
                cRecyclerView.setLayoutManager(cLayoutManager);

//                //        Hardcoded Data to Test Recycler View
//                MutableLiveData<ArrayList<ForumThread>> forumThreadList = new MutableLiveData<ArrayList<ForumThread>>();
//                Message m1 = new Message("filler1", "Liau G Wayne", "123456", "January 28, 2022 at 10:45:00 AM UTC+8", "The marathon is coming up, anyone in?");
//                Message m2 = new Message("filler2", "Lek Jie Kai", "123457", "January 28, 2022 at 10:55:00 AM UTC+8", "I’m in! Shall we start prepping?");
//                ArrayList<Message> mArray1 = new ArrayList<>();
//                mArray1.add(m1);
//                mArray1.add(m2);
//                ArrayList<Message> mArray2 = new ArrayList<>();
//                mArray2.add(m1);
//                ArrayList<Message> mArray3 = new ArrayList<>();
//                mArray3.add(m2);
//                ArrayList<ForumThread> fArray = new ArrayList<>();
//                fArray.add(new ForumThread("fsMgD4ddjRdK1bXLr0Dp", "Cycling Marathon", mArray1));
//                fArray.add(new ForumThread("nnaj7Wr9nd1f4y4RiPSo", "Biking along East Coast", mArray2));
//                fArray.add(new ForumThread("123", "Mandai Loop on 28 Jan", mArray3));
//                fArray.add(new ForumThread("123456", "Best Biking Trail in Singapore", mArray1));
//                forumThreadList.setValue(fArray);

                cRecyclerView.setAdapter(new ForumThreadAdapter(forumThreadArray));
                Log.d(TAG, "onChanged: Successfully set adapter");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
