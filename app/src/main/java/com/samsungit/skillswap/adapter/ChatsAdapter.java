package com.samsungit.skillswap.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Chatroom;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    FirebaseAuth mAuth;
    FirebaseUser user;

    private Context context;
    private List<Chatroom> chatList;

    public ChatsAdapter(Context context, List<Chatroom> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatsAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_cell, parent, false);

        return new ChatViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ChatViewHolder holder, int position) {

        Chatroom chatroom = chatList.get(position);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // show last message
        holder.lastMessage.setText(chatroom.getLastMessageSent());

        // find the other user in the chat
        for (String userId : chatroom.getUsers().keySet()) {

            if (!userId.equals(currentUserId)) {

                FirebaseDatabase.getInstance().getReference("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
                    String displayName = dataSnapshot.child("displayName").getValue(String.class);

                    holder.chatName.setText(displayName);

                    // save to open chat
                    holder.otherUserId = userId;
                    holder.otherUserName = displayName;

                });
            }

        }

        // open chat
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("chatroomId", chatroom.getChatroomId());
                bundle.putString("opId", holder.otherUserId);
                bundle.putString("opName", holder.otherUserName);


                NavController navController = Navigation.findNavController(v);

                navController.navigate(R.id.chatFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView chatName;
        TextView lastMessage;

        String otherUserId;
        String otherUserName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatName = itemView.findViewById(R.id.chatUserName);
            lastMessage = itemView.findViewById(R.id.chat_cell_description);

        }
    }

}