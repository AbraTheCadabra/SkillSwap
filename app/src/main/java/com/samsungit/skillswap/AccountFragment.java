package com.samsungit.skillswap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AccountFragment extends Fragment {



    TextView username, email;
    AppCompatButton logout_btn;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView profilePic;

    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePickLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK
                            && result.getData() != null
                            && result.getData().getData() != null) {

                        selectedImageUri = result.getData().getData();

                        Glide.with(getContext())
                                .load(selectedImageUri)
                                .apply(RequestOptions.circleCropTransform())
                                .into(profilePic);

                        try {
                            Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(
                                    requireActivity()
                                            .getContentResolver()
                                            .openInputStream(selectedImageUri)
                            );

                            if (bitmap == null) return;

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser == null) return;

                            String base64 = encodeImage(bitmap);

                            DatabaseReference ref = FirebaseDatabase.getInstance()
                                    .getReference("users")
                                    .child(currentUser.getUid());

                            ref.child("profilePic").setValue(base64);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Image read failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

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
        profilePic = view.findViewById(R.id.profile_pic);

        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        }


        // load in profile picture
        FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("profilePic").get()
                .addOnSuccessListener(snapshot -> {

                    String base64 = snapshot.getValue(String.class);

                    if (base64 != null) {
                        byte[] decoded = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);

                        Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                        Glide.with(this).load(bitmap).circleCrop().into(profilePic);
                    }
                });



        username.setText(user.getDisplayName());
        email.setText(user.getEmail());


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

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

        // profile picture

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AccountFragment.this).cropSquare().compress(512).maxResultSize(512, 512)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                imagePickLauncher.launch(intent);
                                return null;
                            }
                        });
            }
        });

        return view;
    }

    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] bytes = baos.toByteArray();
        return android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }
}