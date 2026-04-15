package com.samsungit.skillswap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.samsungit.skillswap.domain.Message;
import com.samsungit.skillswap.R;

import java.util.List;

// отображение на экран элемента

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages;
    private static final int MY_VH = 0;
    private static final int OTHER_VH = 1;
    private Context context;
    private Fragment fragment;
    private long currentUserId;

    public MessageAdapter(List<Message> messages, Context context, Fragment fragment, long currentUserId) {
        this.messages = messages;
        this.context = context;
        this.fragment = fragment;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) { // помогает понять, какой layout использовать
        Message message = messages.get(position);

        if (message.getUserIdSender() == currentUserId) {
            return MY_VH;
        }
        return OTHER_VH;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // создание
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == MY_VH)
            return new MyMessageViewHolder(inflater.inflate(R.layout.message_item_my, parent, false));
        else
            return new OtherMessageViewHolder(inflater.inflate(R.layout.message_item_other, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { // отрисовка каждого элемента
        Message message = messages.get(position);
        if (message.getUserIdSender() == currentUserId) {
            holder = (MyMessageViewHolder) holder;
            ((MyMessageViewHolder) holder).tvText.setText(message.getText());
            ((MyMessageViewHolder) holder).tvTime.setText(message.getTimeSent().toString());
        }
        else {
            holder = (OtherMessageViewHolder) holder;
            ((OtherMessageViewHolder) holder).tvText.setText(message.getText());
            ((OtherMessageViewHolder) holder).tvTime.setText(message.getTimeSent().toString());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size(); // кол-во сообщений всего (берётся из таблицы)
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size()-1); // добавляет и отрисовывет ещё один элемент
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        private TextView tvTime;
        public MyMessageViewHolder(View item) {
            super(item);
            tvText = item.findViewById(R.id.tv_messageElement_text);
            tvTime = item.findViewById(R.id.tv_messageElement_time);
        }
    }

    class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        private TextView tvTime;
        public OtherMessageViewHolder(View item) {
            super(item);
            tvText = item.findViewById(R.id.tv_messageElement_text_other);
            tvTime = item.findViewById(R.id.tv_messageElement_time_other);
        }
    }

}
