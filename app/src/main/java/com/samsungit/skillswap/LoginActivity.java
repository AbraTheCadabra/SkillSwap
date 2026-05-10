package com.samsungit.skillswap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samsungit.skillswap.helper.EmailStringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    AppCompatButton login_btn;

    EditText et_email;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // edit text fields
        et_email = findViewById(R.id.email_et);
        et_password = findViewById(R.id.pass_et);

        // button
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });

        // sign up ref
        TextView signup_ref = findViewById(R.id.sign_up_ref);

        signup_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void authenticateUser() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        // instantiate the request queue:
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        // the url we're POSTing to:
        String url = "http://192.168.88.16:9080/api/user/login"; // LOCALHOST DOESN'T WORK, USE IP!!!

        // Set Parameters:
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", et_email.getText().toString());
        params.put("password", et_password.getText().toString());

        // Set Request Object:
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String first_name = response.getString("name");
                            String last_name = response.getString("last_name");
                            String email = response.getString("email");

                            SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();

                            // Open Main Activity:
                            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                            // put strings in editor to be able to use them everywhere
                            editor.putString("first_name", first_name);
                            editor.putString("last_name", last_name);
                            editor.putString("email", email);

                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                            // Start
                            startActivity(goToMain);


                            // Pass Values to Account Activity:
                            // TODO: make chat and pass info to it


                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        }); // End of Set Request Object

        queue.add(jsonObjectRequest);

    }

    // validate email
    public boolean validateEmail() {
        String emailTxt = et_email.getText().toString();

        if (emailTxt.isEmpty()) {
            et_email.setError("Email cannot be empty!");
            return false;
        } else if(!EmailStringHelper.regexEmailValidationPattern(emailTxt)) {
            et_email.setError("Please enter a valid email!");
            return false;
        } else {
            et_email.setError(null);
            return true;
        }
    }

    // validate password
    public boolean validatePassword() {
        String pass = et_password.getText().toString();

        if (pass.isEmpty()) {
            et_password.setError("Last name cannot be empty!");
            return false;
        }
        else {
            et_password.setError(null);
            return true;
        }
    }
}
