package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcinema.R;
import com.example.appcinema.utilities.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstActivity extends AppCompatActivity {
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(v -> {
//            signOut();
        });
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    private void signOut() {
//        showToast("Signing out ...");
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =
//                database.collection(Constants.KEY_COLLECTION_USERS).document(
//
//                )
//    }
}