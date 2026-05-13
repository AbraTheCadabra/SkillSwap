package com.samsungit.skillswap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.samsungit.skillswap.helper.EmailStringHelper;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText first_name, last_name, email, password;
    Button sign_up_btn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        // edit text fields:
        first_name = findViewById(R.id.firstname_et);
        last_name = findViewById(R.id.lastname_et);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.pass_et);

        // sign up button:
        sign_up_btn = findViewById(R.id.signup_btn);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_txt, password_txt;

                email_txt = email.getText().toString();
                password_txt = password.getText().toString();

                if (TextUtils.isEmpty(email_txt)) {
                    email.setError("Email cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(password_txt)) {
                    password.setError("Password cannot be empty!");
                    return;
                } else if (password_txt.length() < 6) {
                    password.setError("Password must consist of more than 6 characters!");
                    return;
                }

                if (TextUtils.isEmpty(first_name.getText().toString())) {
                    first_name.setError("First name cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(last_name.getText().toString())) {
                    last_name.setError("Last name cannot be empty!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    String fullName = first_name.getText().toString() + " " + last_name.getText().toString();

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    UserProfileChangeRequest profileUpdates =
                                            new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(fullName)
                                                    .build();

                                    user.updateProfile(profileUpdates);


                                    Toast.makeText(SignupActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // go to login
        TextView log_in_ref = findViewById(R.id.login_ref);

        log_in_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
