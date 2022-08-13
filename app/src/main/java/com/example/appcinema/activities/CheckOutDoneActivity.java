package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityCheckOutDoneBinding;

public class CheckOutDoneActivity extends AppCompatActivity {
    ActivityCheckOutDoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_done);
        binding = DataBindingUtil.setContentView(CheckOutDoneActivity.this,R.layout.activity_check_out_done);
        binding.setLifecycleOwner(this);
        setListener();
    }

    private void setListener() {
        binding.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckOutDoneActivity.this,FirstActivity.class));
            }
        });
        binding.btnMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutDoneActivity.this,FirstActivity.class);
                intent.putExtra("fragment",3);
                startActivity(intent);
            }
        });
    }
}