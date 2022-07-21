package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.appcinema.R;
import com.example.appcinema.adapter.DateAdapter;
import com.example.appcinema.adapter.TimeAdapter;
import com.example.appcinema.databinding.ActivityChooseBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    ActivityChooseBinding binding;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter1,timeAdapter2,timeAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ChooseActivity.this,R.layout.activity_choose);
        binding.setLifecycleOwner(this);
        List<String> listLocation = new ArrayList<>();
        listLocation.add("Location 1");
        listLocation.add("Location 2");
        listLocation.add("Location 3");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listLocation);
        binding.spPlace.setAdapter(adapter);

        binding.rcDate.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcDate.setLayoutManager(linearLayoutManager);
        binding.rcDate.setItemAnimator( new DefaultItemAnimator());
        dateAdapter = new DateAdapter();
        binding.rcDate.setAdapter(dateAdapter);


        binding.rcCinema1.setHasFixedSize(true);
        LinearLayoutManager ln1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcCinema1.setLayoutManager(ln1);
        binding.rcCinema1.setItemAnimator(new DefaultItemAnimator());
        timeAdapter1 = new TimeAdapter();
        binding.rcCinema1.setAdapter(timeAdapter1);

        binding.rcCinema2.setHasFixedSize(true);
        LinearLayoutManager ln2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcCinema2.setLayoutManager(ln2);
        binding.rcCinema2.setItemAnimator(new DefaultItemAnimator());
        timeAdapter2 = new TimeAdapter();
        binding.rcCinema2.setAdapter(timeAdapter2);

        binding.rcCinema3.setHasFixedSize(true);
        LinearLayoutManager ln3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcCinema3.setLayoutManager(ln3);
        binding.rcCinema3.setItemAnimator(new DefaultItemAnimator());
        timeAdapter3 = new TimeAdapter();
        binding.rcCinema3.setAdapter(timeAdapter3);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this,RoomActivity.class));
            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}