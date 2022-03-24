package com.example.bikerx.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.example.bikerx.ui.home.HomeFragmentDirections;

import java.util.ArrayList;

public class ForumThreadAdapter extends RecyclerView.Adapter<ForumThreadAdapter.ViewHolder>{
    private static final String TAG = "ForumThreadAdapter";
    private ArrayList<ForumThread> forumThreadMutableList;
    private String selectedThreadId;
    private Context cContext;

    public ForumThreadAdapter(ArrayList<ForumThread> forumThreadMutableList) {
        this.forumThreadMutableList = forumThreadMutableList;
    }

    @NonNull
    @Override
    public ForumThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_forum_row_item, parent, false);
        cContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String threadName = forumThreadMutableList.get(position).getThreadName();
        ArrayList<Message> messageArrayList = forumThreadMutableList.get(position).getMessageArrayList();
        holder.forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked " + Integer.toString(position));
                Log.d(TAG, "onClick: threadId " + forumThreadMutableList.get(position).getThreadId());
                Log.d(TAG, "onClick: userId " + ((MainActivity)cContext).getUserId());
                selectedThreadId = forumThreadMutableList.get(position).getThreadId();
//                Bundle cBundle = new Bundle();
//                cBundle.putString("threadId", forumThreadMutableList.get(position).getThreadId());
//                MessageFragment messageFragment = new MessageFragment();
//                messageFragment.setArguments(cBundle);
                NavDirections action = ChatFragmentDirections.actionNavigationChatToStartMessageFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });
        holder.setData(position, threadName, messageArrayList);
    }

    @Override
    public int getItemCount() {return forumThreadMutableList.size();}

    public String getThreadId() {return selectedThreadId;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView forumThreadName;
        private TextView forumThreadDescription;
        private FrameLayout forumRow;
        private Button forumButton;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            forumThreadName = itemView.findViewById(R.id.forumThreadName);
            forumThreadDescription = itemView.findViewById(R.id.forumThreadDescription);
            forumRow = itemView.findViewById(R.id.forumThreadRowItemFrame);
            forumButton = itemView.findViewById(R.id.forumThreadButton);
        }

        public void setData(int position, String threadName, ArrayList<Message> messageArrayList){
            forumThreadName.setText(threadName);
            forumThreadDescription.setText(messageArrayList.get(0).getMessageContent());
            if(position%2==0) {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_odd);
            } else {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_even);
            }
        }
    }
}