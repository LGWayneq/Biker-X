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
import java.util.List;

public class ForumThreadAdapter extends RecyclerView.Adapter<ForumThreadAdapter.ViewHolder>{

    private List<ForumThread> forumThreadList;

    public ForumThreadAdapter(List<ForumThread> forumThreadList) {
        this.forumThreadList = forumThreadList;
    }

    @NonNull
    @Override
    public ForumThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_forum_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String threadName = forumThreadList.get(position).getThreadName();
        ArrayList<Message> messageArrayList = forumThreadList.get(position).getMessageArrayList();

        holder.setData(position, threadName, messageArrayList);
    }

    @Override
    public int getItemCount() {
        return forumThreadList.size();
    }

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
            forumThreadDescription.setText(messageArrayList.get(messageArrayList.size()-1).getMessageContent());
            if(position%2==0) {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_odd);
            } else {
                forumRow.setBackgroundResource(R.color.recycler_view_forum_row_item_forumThreadRowItemFrame_color_even);
            }

        }
    }
}