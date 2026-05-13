package com.samsungit.skillswap.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Listing;
import com.thoughtworks.xstream.XStream;

import org.w3c.dom.Text;

import java.util.List;

public class ListingAdapter extends ArrayAdapter<Listing> {
    FirebaseAuth mAuth;

    public ListingAdapter(Context context, int resource, List<Listing> listings) {
        super(context, resource, listings);
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

        for (String skill : listing.getWantToLearn()) {
            Chip chip = new Chip(getContext());
            chip.setText(skill.toUpperCase());
            chip.setClickable(false);
            chip.setCheckable(false);

            chipGroup2.addView(chip);
        }





        return convertView;


    }
}
