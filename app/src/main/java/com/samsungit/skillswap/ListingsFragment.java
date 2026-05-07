package com.samsungit.skillswap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.samsungit.skillswap.adapter.ListingAdapter;
import com.samsungit.skillswap.domain.Listing;
import com.samsungit.skillswap.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ListingsFragment extends Fragment {
    private ListView listView;
    public static ArrayList<Listing> listings = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listings_fragment, container, false);

        setUpListingData();
        setUpList(view);
        setUpOnClickListener();

        return view;
    }

    private void setUpListingData() {
        listings.add(new Listing(1, new User(1, "User", "John Doe"),
                "Need help with cooking"));

        listings.add(new Listing(2, new User(2, "User", "Jane Doe"),
                "Need help with programming"));
        listings.add(new Listing(3, new User(3, "User", "Jane Doe"),
                "Need help with programming"));
        listings.add(new Listing(4, new User(4, "User", "Jane Doe"),
                "Need help with programming"));
    }

    private void setUpList(View view) {
        listView = view.findViewById(R.id.listings_list_view);

        ListingAdapter adapter = new ListingAdapter(getContext(), 0, listings);
        listView.setAdapter(adapter);


    }

    private void setUpOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Listing selectedListing = (Listing) (listView.getItemAtPosition(position));

                Intent showDetail = new Intent(getContext(), ListingDetail.class);
                showDetail.putExtra("id", position);

                startActivity(showDetail);
            }
        });
    }
}