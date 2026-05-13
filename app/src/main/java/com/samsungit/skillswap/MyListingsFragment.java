package com.samsungit.skillswap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samsungit.skillswap.adapter.ListingAdapter;
import com.samsungit.skillswap.domain.Listing;

import java.util.ArrayList;
import java.util.List;

public class MyListingsFragment extends Fragment {
    public static List<Listing> listings = new ArrayList<>();
    ListView myListings;
    AppCompatButton createNewListingBtn;
    private ListingAdapter adapter;
    FirebaseAuth mAuth;
    public List<Listing> my_listings = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_listings_fragment, container, false);

        myListings = view.findViewById(R.id.my_listings_list);

        my_listings.clear();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("listings");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Listing listing = snapshot.getValue(Listing.class);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (listing != null && listing.getOpId().equals(user.getUid())) {
                    my_listings.add(listing);
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
                    if (listings.get(i).getId() == removedListing.getId()) {;
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

        createNewListingBtn = view.findViewById(R.id.create_listing_btn);


        createNewListingBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.createListingFragment);
        });

        setUpList(view);

        return view;
    }

    private void setUpList(View view) {
        myListings = view.findViewById(R.id.my_listings_list);

        adapter = new ListingAdapter(getContext(), 1, my_listings);

        myListings.setAdapter(adapter);


    }
}
