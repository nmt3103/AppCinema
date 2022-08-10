package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.appcinema.R;
import com.example.appcinema.adapter.DateAdapter;
import com.example.appcinema.adapter.TimeAdapter;
import com.example.appcinema.databinding.ActivityChooseBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.viewmodel.ChooseViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    ActivityChooseBinding binding;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter1,timeAdapter2,timeAdapter3;
    ChooseViewModel viewModel;
    Movie movieChoose;
    String location,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(ChooseActivity.this,R.layout.activity_choose);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ChooseViewModel.class);

        Intent intent = getIntent();
        movieChoose = (Movie) intent.getSerializableExtra("movieChoose");
        binding.tvName.setText(movieChoose.getName());
        obeserverViewModel();
        setListener();
    }

    private void obeserverViewModel() {
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
        timeAdapter1 = new TimeAdapter(new TimeAdapter.TimeOnClick() {
            @Override
            public void selectedTime(String string) {
                location = "Location1";
                time = string;
                timeAdapter2.setSelectedTime(-1);
                timeAdapter3.setSelectedTime(-1);

            }
        });
        binding.rcCinema1.setAdapter(timeAdapter1);

        binding.rcCinema2.setHasFixedSize(true);
        LinearLayoutManager ln2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcCinema2.setLayoutManager(ln2);
        binding.rcCinema2.setItemAnimator(new DefaultItemAnimator());
        timeAdapter2 = new TimeAdapter(new TimeAdapter.TimeOnClick() {
            @Override
            public void selectedTime(String string) {
                location = "Location2";
                time = string;
                timeAdapter1.setSelectedTime(-1);
                timeAdapter3.setSelectedTime(-1);

            }
        });
        binding.rcCinema2.setAdapter(timeAdapter2);

        binding.rcCinema3.setHasFixedSize(true);
        LinearLayoutManager ln3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rcCinema3.setLayoutManager(ln3);
        binding.rcCinema3.setItemAnimator(new DefaultItemAnimator());
        timeAdapter3 = new TimeAdapter(new TimeAdapter.TimeOnClick() {
            @Override
            public void selectedTime(String string) {
                location = "Location3";
                time = string;
                timeAdapter1.setSelectedTime(-1);
                timeAdapter2.setSelectedTime(-1);

            }
        });
        binding.rcCinema3.setAdapter(timeAdapter3);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnNext.setVisibility(View.INVISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.btnNext.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.readListRoomFromFirebase(location);
                viewModel.getListRoomLiveDate().observe(ChooseActivity.this, new Observer<List<Room>>() {
                    @Override
                    public void onChanged(List<Room> rooms) {
                        if (!rooms.isEmpty()){
                            boolean check = false;
                            for (Room room: rooms){
                                if (room.getDate().trim().contains(dateAdapter.getSelectedDateString().trim()) &&
                                        (room.getMovieId() == movieChoose.getId()) &&
                                        room.getTime().trim().contains(time)){
                                    check = true;
                                    Intent  intent = new Intent(ChooseActivity.this,RoomActivity.class);
                                    intent.putExtra("roomChose",room);
                                    intent.putExtra("movieChoose",movieChoose);
                                    startActivity(intent);
                                }
                            }
                            if (check == false){
                                Toast.makeText(ChooseActivity.this, "Do not have that room", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });


//        viewModel.getListRoomLiveDate().observe(this, new Observer<List<Room>>() {
//            @Override
//            public void onChanged(List<Room> rooms) {
//                if (!rooms.isEmpty()){
//                    binding.btnNext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            boolean check = false;
//                            for (Room room: rooms){
//                                if (room.getDate().trim().contains(dateAdapter.getSelectedDateString().trim()) &&
//                                        (room.getMovieId() == movieChoose.getId()) &&
//                                        room.getTime().trim().contains(time)){
//                                    check = true;
//                                    Intent  intent = new Intent(ChooseActivity.this,RoomActivity.class);
//                                    intent.putExtra("roomChose",room);
//                                    intent.putExtra("movieChoose",movieChoose);
//                                    startActivity(intent);
//                                }
//                            }
//                            if (check == false){
//                                Toast.makeText(ChooseActivity.this, "Do not have that room", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this,MovieDetailActivity.class));
                finish();

            }
        });
    }
}