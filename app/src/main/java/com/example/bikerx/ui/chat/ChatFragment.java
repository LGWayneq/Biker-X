package com.example.bikerx.ui.chat;

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
import android.widget.TextView;

import com.example.bikerx.R;
import com.example.bikerx.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private ChatViewModel cViewModel;
    private FragmentHomeBinding cBinding;

    private RecyclerView cRecyclerView;
    private List<ForumThread> forumThreadList;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        cViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
//
//        cBinding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = cBinding.getRoot();


//        Hardcoded Data to Test Recycler View
        forumThreadList = new ArrayList<>();
        Message m1 = new Message("filler1", "Liau G Wayne", "123456", "January 28, 2022 at 10:45:00 AM UTC+8", "The marathon is coming up, anyone in?");
        Message m2 = new Message("filler2", "Lek Jie Kai", "123457", "January 28, 2022 at 10:55:00 AM UTC+8", "I’m in! Shall we start prepping?");
        ArrayList<Message> mArray1 = new ArrayList<>();
        mArray1.add(m1);
        mArray1.add(m2);
        ArrayList<Message> mArray2 = new ArrayList<>();
        mArray2.add(m1);
        ArrayList<Message> mArray3 = new ArrayList<>();
        mArray3.add(m2);
        forumThreadList.add(new ForumThread("fsMgD4ddjRdK1bXLr0Dp", "Cycling Marathon", mArray1));
        forumThreadList.add(new ForumThread("nnaj7Wr9nd1f4y4RiPSo", "Biking along East Coast", mArray2));
        forumThreadList.add(new ForumThread("123", "Mandai Loop on 28 Jan", mArray3));
        forumThreadList.add(new ForumThread("123456", "Best Biking Trail in Singapore", mArray1));

        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        cRecyclerView = view.findViewById(R.id.forumThreadRecycleView);
        cRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        cRecyclerView.setLayoutManager(mLayoutManager);
        cRecyclerView.setAdapter(new ForumThreadAdapter(forumThreadList));

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        RecyclerViewFragment fragment = new RecyclerViewFragment();
//        transaction.replace(R.id.sample_content_fragment, fragment);
//        transaction.commit();

        return view;

//        return root;
    }

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = mBinding.getRoot();
//
//        return root;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cBinding = null;
    }

//    private void bindButtons() {
//        cBinding.ownRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavDirections action = HomeFragmentDirections.actionNavigationHomeToStartCyclingFragment();
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//    }
}
