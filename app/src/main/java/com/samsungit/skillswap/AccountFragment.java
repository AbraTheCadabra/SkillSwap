package com.samsungit.skillswap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {

    TextView username, email;
    AppCompatButton logout_btn;
    FirebaseAuth auth;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.account_fragment,
                container,
                false
        );

        auth = FirebaseAuth.getInstance();

        username = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email_acc);

        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        }


        username.setText(user.getDisplayName());
        email.setText(user.getEmail());


        // volley old
//        SharedPreferences prefs = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
//
//        String first_name = prefs.getString("first_name", "John");
//        String last_name = prefs.getString("last_name", "Doe");
//        String emailTxt = prefs.getString("email", "johndoe@email.com");
//
//        username.setText(first_name + " " + last_name);
//        email.setText(emailTxt);
//

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

//        db.child("listings")
//                .child("testItem")
//                .setValue("hello");

        logout_btn = view.findViewById(R.id.logout_btn);


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        AppCompatButton myListingsBtn = view.findViewById(R.id.my_listings_btn);


        myListingsBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.myListingsFragment);
        });

        return view;
    }

}