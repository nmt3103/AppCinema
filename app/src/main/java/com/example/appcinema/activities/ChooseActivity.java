package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appcinema.R;
import com.example.appcinema.adapter.DateAdapter;
import com.example.appcinema.adapter.TimeAdapter;
import com.example.appcinema.databinding.ActivityChooseBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Room;
import com.example.appcinema.viewmodel.ChooseViewModel;

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
        if (!intent.getExtras().isEmpty()){
            location ="";
            time="";
            movieChoose = (Movie) intent.getSerializableExtra("movieChoose");
            binding.tvName.setText(movieChoose.getName());
            obeserverViewModel();
            setListener();
        }

    }

    private void obeserverViewModel() {

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
                viewModel.readListRoomFromFirebase(location,dateAdapter.getSelectedDateString().trim(),time,movieChoose.getId());
                viewModel.getListRoomLiveDate().observe(ChooseActivity.this, new Observer<Room>() {
                    @Override
                    public void onChanged(Room room) {
                        if (room != null){
                            Intent  intent = new Intent(ChooseActivity.this,RoomActivity.class);
                            Log.d("onchanged",room.getId().toString());
                            intent.putExtra("roomChose",room);
                            intent.putExtra("movieChoose",movieChoose);
                            intent.putExtra("location",location);
                            startActivity(intent);
                        }
                    }
                });
            }
        });


        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}