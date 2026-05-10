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

public class AccountFragment extends Fragment {

    TextView username, email;
    AppCompatButton logout_btn;

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


        // text view objects:
        username = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email_acc);


        // print user data
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        String first_name = prefs.getString("first_name", "John");
        String last_name = prefs.getString("last_name", "Doe");
        String emailTxt = prefs.getString("email", "johndoe@email.com");

        username.setText(first_name + " " + last_name);
        email.setText(emailTxt);

        // Log Out button:
        logout_btn = view.findViewById(R.id.logout_btn);

        // Log Out button functionality:
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUserOut();
            }
        });

        return view;
    }
    public void logUserOut() {
        username.setText(null);
        email.setText(null);

        // bring user back to login screen

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        // Remove saved Login data

        editor.clear();
        editor.apply();

        // Open login screen
        Intent intent = new Intent(requireActivity(), LoginActivity.class);

        // Prevent user from going back
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

}