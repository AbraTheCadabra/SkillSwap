package com.samsungit.skillswap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsungit.skillswap.adapter.ListingAdapter;
import com.samsungit.skillswap.domain.Listing;
import com.samsungit.skillswap.domain.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Listing> listings = new ArrayList<Listing>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_frag);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void setupListingData() { Listing listing = new Listing(1, new User(1, "User", "John Doe"), "Need help with learning how to cook"); listings.add(listing); Listing listing2 = new Listing(2, new User(1, "User", "Jane Doe"), "Need help with programming"); listings.add(listing2); Listing listing3 = new Listing(2, new User(1, "User", "Jane Doe"), "Need help with house chores"); listings.add(listing3); }
    private void setupList() { listView = findViewById(R.id.listings_list_view); ListingAdapter adapter = new ListingAdapter(getApplicationContext(), 0, listings); listView.setAdapter(adapter); }

    }


