package com.samsungit.skillswap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateListingFragment extends Fragment {

    AppCompatButton createNewListingBtn;
    ImageButton backBtn;
    EditText wantInput, teachInput, descriptionInput;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_listing_fragment, container, false);

        createNewListingBtn = view.findViewById(R.id.create_new_listing_btn);

        // edit text objects
        wantInput = view.findViewById(R.id.want_to_learn_et);
        teachInput = view.findViewById(R.id.can_teach_et);
        descriptionInput = view.findViewById(R.id.listing_description_et);
        backBtn = view.findViewById(R.id.create_listing_back_btn);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("listings");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        createNewListingBtn.setOnClickListener(v -> {

            String descriptionText = descriptionInput.getText().toString();
            String wantText = wantInput.getText().toString();
            String teachText = teachInput.getText().toString();

            List<String> wantToLearn = Arrays.asList(wantText.split(","));
            List<String> canTeach = Arrays.asList(teachText.split(","));

            String id = db.push().getKey();

            Map<String, Object> listing = new HashMap<>();
            listing.put("opId", user.getUid()); // OP = original poster
            listing.put("wantToLearn", wantToLearn);
            listing.put("canTeach", canTeach);
            listing.put("timestamp", System.currentTimeMillis());
            listing.put("description", descriptionText);
            listing.put("id", id);
            listing.put("opName", user.getDisplayName());


            db.child(id).setValue(listing);

            Toast.makeText(view.getContext(), "Listing created successfully!", Toast.LENGTH_LONG).show();

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.myListingsFragment);
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.myListingsFragment);
            }
        });

        return view;
    }


}
