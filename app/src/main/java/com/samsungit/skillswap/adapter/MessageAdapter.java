package com.samsungit.skillswap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Message;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Message> messages;

    private final int VIEW_TYPE_MY_MESSAGE = 1;
    private final int VIEW_TYPE_OTHER_MESSAGE = 2;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {

        Message message = messages.get(position);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (message.getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_MY_MESSAGE;
        }

        return VIEW_TYPE_OTHER_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MY_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_my, parent, false);
            return new MyMessageViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_other, parent, false);
        return new MyMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        String time = new SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(new Date(message.getTimestamp()));

        if (holder instanceof MyMessageViewHolder) {

            ((MyMessageViewHolder) holder).messageText.setText(message.getMessage());

            ((MyMessageViewHolder) holder).messageTime.setText(time);
        } else {

            ((OtherMessageViewHolder) holder).messageText.setText(message.getMessage());

            ((OtherMessageViewHolder) holder).messageTime.setText(time);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    // ??
    static class MyMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageTime;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.tv_messageElement_text);

            messageTime = itemView.findViewById(R.id.tv_messageElement_time);
        }
    }

    static class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageTime;

        public OtherMessageViewHolder(
                @NonNull View itemView) {

            super(itemView);

            messageText = itemView.findViewById(R.id.tv_messageElement_text);

            messageTime = itemView.findViewById(R.id.tv_messageElement_time);
        }
    }


}
