package com.samsungit.skillswap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Chatroom;
import com.samsungit.skillswap.domain.Listing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditListingAdapter extends ArrayAdapter<Listing> {
    FirebaseAuth mAuth;
    FirebaseUser user;

    public EditListingAdapter(Context context, int resource, List<Listing> listings) {
        super(context, resource, listings);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Listing listing = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_listing_cell, parent, false);
        }
        TextView description = convertView.findViewById(R.id.cell_description);
        TextView name = convertView.findViewById(R.id.lis_profile_name);

        AppCompatButton deleteBtn = convertView.findViewById(R.id.delete_listing_btn);

        ImageView profilePic = convertView.findViewById(R.id.profile_pic);

        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("listings").child(listing.getId());

        // profile picture
        refUser.child("profilePic").get().addOnSuccessListener(snapshot -> {

            String base64 = snapshot.getValue(String.class);

            if (base64 != null) {
                byte[] decoded = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);

                Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.length);

                Glide.with(getContext()).load(bitmap).circleCrop().into(profilePic);
            } else {
                profilePic.setImageResource(R.drawable.def_profile);
            }
        });


        // delete listing button
        deleteBtn.setOnClickListener(v -> {

            String listingId = String.valueOf(listing.getId());

            ref.removeValue().addOnSuccessListener(unused -> {

                // loop through all of the chatrooms, find the ones with the listing id and delete them
                DatabaseReference refChatrooms = FirebaseDatabase.getInstance().getReference("chatrooms");
                refChatrooms.get().addOnSuccessListener(dataSnapshot -> {
                    for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                        String chatroomId = chatSnapshot.getKey();

                        if(chatroomId.contains(listingId)) {
                            chatSnapshot.getRef().removeValue();
                        }
                    }
                });

                remove(listing);
                notifyDataSetChanged();

                Toast.makeText(getContext(), "Listing deleted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
        });


        description.setText(listing.getDescription());

        if (listing.getOpId().equals(user.getUid()))
            name.setText(user.getDisplayName());
        else
            name.setText(listing.getOpName());

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


        return convertView;


    }
}
