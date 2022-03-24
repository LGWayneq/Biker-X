package com.example.bikerx.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.MainActivity;
import com.example.bikerx.R;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private ArrayList<Message> messageMutableList;
    private Context mContext;

    public MessageAdapter(ArrayList<Message> messageMutableList) {
        this.messageMutableList = messageMutableList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message_row_item, parent, false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userId = messageMutableList.get(position).getUserId();
        String name = messageMutableList.get(position).getUserName();
        String messageID = messageMutableList.get(position).getMessageID();
        String content = messageMutableList.get(position).getMessageContent();

        Date timestamp = messageMutableList.get(position).getTime().toDate();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext.getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext.getApplicationContext());
        String time = timeFormat.format(timestamp);
        String date = dateFormat.format(timestamp);
        String previousDate = null;
        if (position>=1) {
            Date previousTimestamp = messageMutableList.get(position-1).getTime().toDate();
            previousDate = dateFormat.format(previousTimestamp);
        }

        holder.setData(position, ((MainActivity) mContext).getUserId(), userId, name, messageID, date, time, content, previousDate);
    }

    @Override
    public int getItemCount() {return messageMutableList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView messageName;
        private TextView messageContent;
        private TextView messageTime;
        private TextView messageDate;
        private ConstraintLayout messageConstraint;
        private ConstraintLayout messageDateConstraint;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            messageName = itemView.findViewById(R.id.messageName);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageTime = itemView.findViewById(R.id.messageTime);
            messageDate = itemView.findViewById(R.id.messageDate);
            messageConstraint = itemView.findViewById(R.id.messageRow);
            messageDateConstraint = itemView.findViewById(R.id.messageDateRow);
        }

        public void setData(int position, String currentUserId, String userId, String name, String messageID, String date, String time, String content, String previousDate){
            messageName.setText(name);
            messageContent.setText(content);
            messageTime.setText(time);
            messageDate.setText(date);
            if (date.equals(previousDate)) {
                messageDateConstraint.setVisibility(View.GONE);
            }
            if(userId.equals(currentUserId)) {
                final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) messageConstraint.getLayoutParams();
                layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
                layoutParams.rightToRight = R.id.messageRowItemFrame;
                layoutParams.setMarginEnd(20);
                messageConstraint.setLayoutParams(layoutParams);

                messageConstraint.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.recycler_view_message_row_item_user_color));
            }
        }
    }
}