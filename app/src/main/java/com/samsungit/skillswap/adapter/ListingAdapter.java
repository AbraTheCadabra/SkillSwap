package com.samsungit.skillswap.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.ChatFragment;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Listing;
import com.thoughtworks.xstream.XStream;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingAdapter extends ArrayAdapter<Listing> {
    FirebaseAuth mAuth;
    FirebaseUser user;

    public ListingAdapter(Context context, int resource, List<Listing> listings) {
        super(context, resource, listings);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Listing listing = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listing_cell, parent, false);
        }
        TextView description = (TextView) convertView.findViewById(R.id.cell_description);
        TextView name = (TextView) convertView.findViewById(R.id.lis_profile_name);


        description.setText(listing.getDescription());
        name.setText(listing.getOpName());
        // pfp.setImageResource(listing.getCreator().getPfp()); TODO: implement later

        // turn wantToLearn into chips
        ChipGroup chipGroup = convertView.findViewById(R.id.want_to_learn_chip_group);

        // remove old chips
        chipGroup.removeAllViews();

        for (String skill : listing.getWantToLearn()) {
            Chip chip = new Chip(getContext());
            chip.setText(skill.toUpperCase());
            chip.setClickable(false);
            chip.setCheckable(false);

            chipGroup.addView(chip);
        }

        // turn canTeach into chips
        ChipGroup chipGroup2 = convertView.findViewById(R.id.can_teach_chip_group);

        // remove old chips
        chipGroup2.removeAllViews();

        for (String skill : listing.getCanTeach()) {
            Chip chip = new Chip(getContext());
            chip.setText(skill.toUpperCase());
            chip.setClickable(false);
            chip.setCheckable(false);

            chipGroup2.addView(chip);
        }

        // respond to listing and make chat with OP
        AppCompatButton respondBtn = convertView.findViewById(R.id.respond_btn);


        respondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opId = listing.getOpId();
                String opName = listing.getOpName();

                // generate chatroom id while making sure that we don't create a new chatroom on accident
                String chatroomId;
                if (user.getUid().compareTo(opId) < 0) {
                    chatroomId = user.getUid() + "_" + opId;
                } else {
                    chatroomId = opId + "_" + user.getUid();
                }

                // make sure that the user doesn't create a chat with themselves
                if (user.getUid().equals(opId)) {
                    Toast.makeText(getContext(), "ERROR: CANNOT MAKE A CHAT WITH YOURSELF", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference chatroomsRef = FirebaseDatabase.getInstance().getReference("chatrooms");

                chatroomsRef.child(chatroomId).get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        // create chatroom only if it's missing
                        if (!task.getResult().exists()) {
                            Map<String, Object> chatroomMap = new HashMap<>();

                            Map<String, Object> usersMap = new HashMap<>();

                            usersMap.put(user.getUid(), true);
                            usersMap.put(opId, true);

                            chatroomMap.put("users", usersMap);
                            long timestamp = System.currentTimeMillis();

                            chatroomMap.put("createdAt", timestamp);

                            chatroomMap.put("lastMessage", "");
                            chatroomMap.put("lastMessageSender", "");
                            chatroomMap.put("lastMessageSentTimestamp", 0);

                            chatroomsRef.child(chatroomId).setValue(chatroomMap);
                        }
                    }

                });

                Bundle bundle = new Bundle();
                bundle.putString("opId", opId);
                bundle.putString("opName", opName);
                bundle.putString("chatroomId", chatroomId);



                NavController navController = Navigation.findNavController(v);

                // navigate to chat
                navController.navigate(R.id.chatFragment, bundle);
            }
        });

        return convertView;


    }
}
