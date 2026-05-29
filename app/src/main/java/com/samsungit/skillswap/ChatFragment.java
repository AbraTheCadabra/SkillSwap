package com.samsungit.skillswap;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsungit.skillswap.adapter.MessageAdapter;
import com.samsungit.skillswap.domain.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {
    String opId;
    String opName;
    String chatroomId;

    FirebaseAuth mAuth;
    FirebaseUser user;

    DatabaseReference dbChat;

    TextView chatName;

    EditText messageInput;
    ImageButton sendBtn;
    ImageButton backBtn;
    RecyclerView recyclerView;

    ImageView profilePic;

    List<Message> messageList;
    MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        chatName = view.findViewById(R.id.chat_name);
        messageInput = view.findViewById(R.id.msg_input);
        sendBtn = view.findViewById(R.id.send_btn);
        backBtn = view.findViewById(R.id.back_btn);
        recyclerView = view.findViewById(R.id.chat_rec);
        profilePic = view.findViewById(R.id.profile_pic);

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Bundle args = getArguments();

        if (args != null) {
            opId = args.getString("opId");
            opName = args.getString("opName");
            chatroomId = args.getString("chatroomId");

            chatName.setText(opName);
        }

        dbChat = FirebaseDatabase.getInstance().getReference("chatrooms").child(chatroomId);

        loadMessages();

        // load pfp
        // profile picture
        FirebaseDatabase.getInstance().getReference("users").child(opId).child("profilePic").get().addOnSuccessListener(dataSnapshot -> {
            String base64 = dataSnapshot.getValue(String.class);

            if (base64 != null) {
                byte[] decoded = android.util.Base64.decode(base64, Base64.DEFAULT);

                Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.length);

                Glide.with(getContext()).load(bitmap).circleCrop().into(profilePic);
            }
            else {
               profilePic.setImageResource(R.drawable.def_profile);
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString().trim();
                if (message.isEmpty()) {
                    return;
                }
                sendMessageToUser(message);
            }
        });


        // back btn
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_chats);
            }
        });

        return view;
    }

    void sendMessageToUser(String message) {
        long timestamp = System.currentTimeMillis();

        dbChat.child("lastMessageSentTimestamp").setValue(timestamp);
        dbChat.child("lastMessageSender").setValue(user.getUid());


        Map<String, Object> messageHashMap = new HashMap<>();
        messageHashMap.put("message", message);
        messageHashMap.put("senderId", user.getUid());
        messageHashMap.put("timestamp", timestamp);

        String messageId = dbChat.child("messages").push().getKey();

        dbChat.child("messages").child(messageId).setValue(messageHashMap);

        dbChat.child("lastMessage").setValue(message);

        // Message messageModel = new Message(message, user.getUid(), timestamp); // ??

        messageInput.setText("");


    }

    void loadMessages() {

        dbChat.child("messages").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                messageList.clear();

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Message message = dataSnapshot.getValue(Message.class);

                                    messageList.add(message);
                                }

                                adapter.notifyDataSetChanged();

                                if (!messageList.isEmpty())
                                    recyclerView.scrollToPosition(messageList.size() - 1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
    }

}
