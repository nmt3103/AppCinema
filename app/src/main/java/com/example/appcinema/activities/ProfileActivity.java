package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.example.appcinema.MainActivity;
import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityProfileBinding;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.ProfileViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    ProfileViewModel viewModel;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ProfileActivity.this,R.layout.activity_profile);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        preferenceManager = new PreferenceManager(this);
        setListener();
        loadUserDetails();
    }

    private void setListener() {
        binding.tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfileActivity.class));
            }
        });
        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.clear();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FirstActivity.class));
                finish();
            }
        });
    }

    private void loadUserDetails() {
        binding.tvEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        binding.tvName.setText(preferenceManager.getString(Constants.KEY_NAME));
        preferenceManager = new PreferenceManager(this);
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }
}