package com.samsungit.skillswap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsungit.skillswap.adapter.ChatsAdapter;
import com.samsungit.skillswap.domain.Chatroom;

import java.util.ArrayList;
import java.util.List;
public class ChatsFragment extends Fragment {

    List<Chatroom> chatList;
    ChatsAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_fragment, container, false);

        recyclerView = view.findViewById(R.id.chats_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatList = new ArrayList<>();

        adapter = new ChatsAdapter(getContext(), chatList);

        recyclerView.setAdapter(adapter);

        loadChats();

        return view;
    }

    private void loadChats() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("chatrooms");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {

                    Chatroom chatroom = chatSnapshot.getValue(Chatroom.class);

                    if (chatroom == null) {
                        continue;
                    }

                    chatroom.setChatroomId(chatSnapshot.getKey());

                    // !!!
                    // check if the current user is in the chatroom
                    if (chatroom.getUsers() != null && chatroom.getUsers().containsKey(currentUserId)) {
                        chatList.add(chatroom);
                    }

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}