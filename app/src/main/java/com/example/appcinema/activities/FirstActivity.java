package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcinema.MainActivity;
import com.example.appcinema.R;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

public class FirstActivity extends AppCompatActivity {
    TextView tvLogout;
    private PreferenceManager preferenceManager;
//    private FirebaseFirestore database;
    RoundedImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        tvLogout = findViewById(R.id.tvLogout);
        imgProfile = findViewById(R.id.imageProfile);

        preferenceManager = new PreferenceManager(getApplicationContext());




        loadUserDetails();
        setListeners();

    }



    private void loadUserDetails() {
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imgProfile.setImageBitmap(bitmap);
    }

    private void setListeners() {
        tvLogout.setOnClickListener(v -> {
            signOut();
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        showToast("Signing out ...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                    preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }
}