package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityCheckOutDoneBinding;

public class CheckOutDoneActivity extends AppCompatActivity {
    ActivityCheckOutDoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_done);
    }
}