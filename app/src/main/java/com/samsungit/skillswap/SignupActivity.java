package com.samsungit.skillswap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.samsungit.skillswap.helper.EmailStringHelper;

import java.util.HashMap;
import java.util.Map;

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

        // the url we're POSTing to:
        String url = "http://192.168.88.16:9080/api/user/register"; // !!!!! IP MATTERS, LOCALHOST DOESN'T WORK !!!!
        // TODO: REMEMBER THIS FOR WHEN YOU'RE GOING TO MAKE LISTINGS AND CHATS:
        // JSONOBJECTREQUEST, JSONARRAYREQUEST

        // String Request Object:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("success")) {
                    first_name.setText(null);
                    last_name.setText(null);
                    email.setText(null);
                    password.setText(null);

                    Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                }
                // end of response If block

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignupActivity.this, "Registration unsuccessful.", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", first_name.getText().toString()); // TODO: rename name to first_name
                params.put("last_name", last_name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        }; // end of string request object


        queue.add(stringRequest); // !!!
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
