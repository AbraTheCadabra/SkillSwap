package com.samsungit.skillswap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.samsungit.skillswap.domain.Listing;

public class ListingDetail extends AppCompatActivity {
    Listing selectedListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listing_detail);

        getSelectedListing();
        setValues();
    }

    private void getSelectedListing() {
        Intent previousIntent = getIntent();
        int id = getIntent().getIntExtra("id", -1);
        int position = getIntent().getIntExtra("id", -1);
        selectedListing = ListingsFragment.listings.get(position);
    }

    private void setValues() {
        TextView description = (TextView) findViewById(R.id.cell_description);
        TextView name = (TextView) findViewById(R.id.lis_profile_name);


        description.setText(selectedListing.getDescription());
        name.setText(selectedListing.getCreator().getName());
    }
}