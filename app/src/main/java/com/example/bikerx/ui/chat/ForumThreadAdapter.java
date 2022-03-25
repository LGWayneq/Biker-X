package com.example.bikerx.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;

import java.util.ArrayList;

public class ForumThreadAdapter extends RecyclerView.Adapter<ForumThreadAdapter.ViewHolder>{
    private static final String TAG = "ForumThreadAdapter";
    private ArrayList<ForumThread> forumThreadMutableList;
    private Context cContext;
    private FragmentCommunication cCommunicator;

    public ForumThreadAdapter(ArrayList<ForumThread> forumThreadMutableList, FragmentCommunication cCommunicator) {
        this.forumThreadMutableList = forumThreadMutableList;
        this.cCommunicator = cCommunicator;
    }

    @NonNull
    @Override
    public ForumThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_forum_row_item, parent, false);
        cContext = view.getContext();
        return new ViewHolder(view, cCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String threadId = forumThreadMutableList.get(position).getThreadId();
        String threadName = forumThreadMutableList.get(position).getThreadName();
        ArrayList<Message> messageArrayList = forumThreadMutableList.get(position).getMessageArrayList();

        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("threadId", forumThreadMutableList.get(position).getThreadId());
        bundle.putString("threadName", forumThreadMutableList.get(position).getThreadName());
        messageFragment.setArguments(bundle);

        holder.setData(position, threadId, threadName, messageArrayList);
    }

    @Override
    public int getItemCount() {return forumThreadMutableList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView forumThreadName;
        private TextView forumThreadDescription;
        private FrameLayout forumRow;
        private FrameLayout forumThreadRowFrame;
        private FragmentCommunication cCommunication;
        private String threadId;
        private String threadName;

        public ViewHolder(@NonNull View itemView, FragmentCommunication Communicator){
            super(itemView);
            forumThreadName = itemView.findViewById(R.id.forumThreadName);
            forumThreadDescription = itemView.findViewById(R.id.forumThreadDescription);
            forumRow = itemView.findViewById(R.id.forumThreadRowItemFrame);
            forumThreadRowFrame = itemView.findViewById(R.id.forumThreadRowItemFrame);
            forumThreadRowFrame.setOnClickListener(this);
            cCommunication = Communicator;
        }

        public void setData(int position, String threadId, String threadName, ArrayList<Message> messageArrayList){
            this.threadId = threadId;
            this.threadName = threadName;

            forumThreadName.setText(threadName);
            forumThreadDescription.setText(messageArrayList.get(0).getMessageContent());
            if(position%2==0) {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_odd);
            } else {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_even);
            }
        }

        @Override
        public void onClick(View view) {
            cCommunication.onForumClick(this.threadId, this.threadName);
        }
    }
}