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
    private static final String TAG = "MessageAdapter";
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
        Date timestamp = messageMutableList.get(position).getTime().toDate();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext.getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext.getApplicationContext());
        String time = timeFormat.format(timestamp);
        String date = dateFormat.format(timestamp);
        String content = messageMutableList.get(position).getMessageContent();

        holder.setData(position, ((MainActivity) mContext).getUserId(), userId, name, messageID, date, time, content);
    }

    @Override
    public int getItemCount() {return messageMutableList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView messageName;
        private TextView messageContent;
        private TextView messageTime;
        private TextView messageDate;
        private ConstraintLayout messageConstraint;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            messageName = itemView.findViewById(R.id.messageName);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageTime = itemView.findViewById(R.id.messageTime);
            messageDate = itemView.findViewById(R.id.messageDate);
            messageConstraint = itemView.findViewById(R.id.messageRow);
        }

        public void setData(int position, String currentUserId, String userId, String name, String messageID, String date, String time, String content){
            messageName.setText(name);
            messageContent.setText(content);
            messageTime.setText(time);
            messageDate.setText(date);
            Log.d(TAG, "setData: userId " + userId);
            Log.d(TAG, "setData: currentUserId " + currentUserId);
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