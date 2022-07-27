package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityCheckOutBinding;

public class CheckOutActivity extends AppCompatActivity {
    ActivityCheckOutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CheckOutActivity.this,R.layout.activity_check_out);
        binding.setLifecycleOwner(this);
        setListener();
    }

    private void setListener() {
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, RoomActivity.class);
                intent.putExtra("IsSuccess", true);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}