package com.samsungit.skillswap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.adapter.ListingAdapter;
import com.samsungit.skillswap.domain.Listing;
import com.samsungit.skillswap.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ListingsFragment extends Fragment {
    private ListView listView;
    public static List<Listing> listings = new ArrayList<>();
    Firebase myFirebase;
    private ListingAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listings_fragment, container, false);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("listings");

        listings.clear();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Listing listing = snapshot.getValue(Listing.class);
                if (listing != null) {
                    listings.add(listing);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Listing updatedListing = snapshot.getValue(Listing.class);
                if (updatedListing == null) return;

                // find and replace the existing listing with the same id
                for (int i = 0; i < listings.size(); i++) {
                    if (listings.get(i).getId() == updatedListing.getId()) {
                        listings.set(i, updatedListing);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Listing removedListing = snapshot.getValue(Listing.class);
                if (removedListing == null) return;

                // remove the listing from the list
                for (int i = 0; i < listings.size(); i++) {
                    if (listings.get(i).getId() == removedListing.getId()) {
                        listings.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });



        // setUpListingData();
        setUpList(view);
        // setUpOnClickListener();

        return view;
    }

//    private void setUpListingData() {
//        listings.add(new Listing(1, new User(1, "User", "John Doe"),
//                "Need help with cooking"));
//
//        listings.add(new Listing(2, new User(2, "User", "Jane Doe"),
//                "Need help with programming"));
//        listings.add(new Listing(3, new User(3, "User", "Jane Doe"),
//                "Need help with programming"));
//        listings.add(new Listing(4, new User(4, "User", "Jane Doe"),
//                "Need help with programming"));
//    }

    private void setUpList(View view) {
        listView = view.findViewById(R.id.listings_list_view);

        adapter = new ListingAdapter(getContext(), 1, listings);

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