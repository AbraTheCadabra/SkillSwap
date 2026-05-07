package com.samsungit.skillswap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.samsungit.skillswap.helper.EmailStringHelper;

public class SignupActivity extends AppCompatActivity {
    EditText first_name, last_name, email, password;
    Button sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

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
                processFormFields();
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

    // process form and registrate user
    public void processFormFields() {
        // check for errors in forms:
        if (!validateFirstName() || !validateLastName() || !validateEmail() || !validatePassword()) {
            return;
        }

        // instantiate the request queue:
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);


        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
    }

    // validate first name
    public boolean validateFirstName() {
        String firstname = first_name.getText().toString();

        if (firstname.isEmpty()) {
            first_name.setError("First name cannot be empty!");
            return false;
        }
        else {
            first_name.setError(null);
            return true;
        }
    }

    // validate last name
    public boolean validateLastName() {
        String lastName = last_name.getText().toString();

        if (lastName.isEmpty()) {
            last_name.setError("Last name cannot be empty!");
            return false;
        }
        else {
            last_name.setError(null);
            return true;
        }
    }

    // validate email
    public boolean validateEmail() {
        String emailTxt = email.getText().toString();

        if (emailTxt.isEmpty()) {
            email.setError("Email cannot be empty!");
            return false;
        } else if(!EmailStringHelper.regexEmailValidationPattern(emailTxt)) {
            email.setError("Please enter a valid email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    // validate password
    public boolean validatePassword() {
        String pass = password.getText().toString();

        if (pass.isEmpty()) {
            password.setError("Last name cannot be empty!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }
}
