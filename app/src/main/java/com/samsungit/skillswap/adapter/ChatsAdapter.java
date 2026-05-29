package com.samsungit.skillswap.adapter;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Chatroom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // find other user in chat
        for (String id : chatroom.getUsers().keySet()) {
            if (!id.equals(currentUserId)) {
                holder.otherUserId = id;
                break;
            }
        }

        // profile picture
        FirebaseDatabase.getInstance().getReference("users").child(holder.otherUserId).child("profilePic").get().addOnSuccessListener(dataSnapshot -> {
           String base64 = dataSnapshot.getValue(String.class);

           holder.otherUserPfpBase64 = String.valueOf(base64);

           if (base64 != null) {
               byte[] decoded = android.util.Base64.decode(base64, Base64.DEFAULT);

               Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.length);

               Glide.with(context).load(bitmap).circleCrop().into(holder.profilePic);
           }
           else {
               holder.profilePic.setImageResource(R.drawable.def_profile);
           }
        });


        // show last message
        String lastMessage = chatroom.getLastMessage();

        String senderId = chatroom.getLastMessageSender();

        if (senderId == null) {
            holder.lastMessage.setText(lastMessage);

        } else if (senderId.equals(currentUserId)) {
            holder.lastMessage.setText("You: " + lastMessage);

        } else {
            holder.lastMessage.setText("Them: " + lastMessage);
        }

        // find the other user in the chat
        for (String userId : chatroom.getUsers().keySet()) {
            if (!userId.equals(currentUserId)) {
                FirebaseDatabase.getInstance().getReference("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
                    String displayName = dataSnapshot.child("displayName").getValue(String.class);

                    holder.chatName.setText(displayName);

                    // save to open chat
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
        ImageView profilePic;

        String otherUserId;
        String otherUserName;
        String otherUserPfpBase64;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatName = itemView.findViewById(R.id.chatUserName);
            lastMessage = itemView.findViewById(R.id.chat_cell_description);
            profilePic = itemView.findViewById(R.id.profile_pic);

        }
    }

}