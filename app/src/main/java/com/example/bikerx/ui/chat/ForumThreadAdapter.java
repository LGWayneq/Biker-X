package com.example.bikerx.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;

import java.util.ArrayList;

public class ForumThreadAdapter extends RecyclerView.Adapter<ForumThreadAdapter.ViewHolder>{
    private static final String TAG = "ForumThreadAdapter";
    private ArrayList<ForumThread> forumThreadMutableList;

    public ForumThreadAdapter(ArrayList<ForumThread> forumThreadMutableList) {
        this.forumThreadMutableList = forumThreadMutableList;
    }

    @NonNull
    @Override
    public ForumThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_forum_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String threadName = forumThreadMutableList.get(position).getThreadName();
        ArrayList<Message> messageArrayList = forumThreadMutableList.get(position).getMessageArrayList();

        holder.setData(position, threadName, messageArrayList);
    }

    @Override
    public int getItemCount() {return forumThreadMutableList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView forumThreadName;
        private TextView forumThreadDescription;
        private FrameLayout forumRow;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            forumThreadName = itemView.findViewById(R.id.forumThreadName);
            forumThreadDescription = itemView.findViewById(R.id.forumThreadDescription);
            forumRow = itemView.findViewById(R.id.forumThreadRowItemFrame);
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