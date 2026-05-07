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

import com.samsungit.skillswap.R;
import com.samsungit.skillswap.domain.Listing;

import org.w3c.dom.Text;

import java.util.List;

public class ListingAdapter extends ArrayAdapter<Listing> {
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
        name.setText(listing.getCreator().getName());
        // pfp.setImageResource(listing.getCreator().getPfp());



        return convertView;
    }
}
