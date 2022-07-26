package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appcinema.R;
import com.example.appcinema.adapter.SlotAdapter;
import com.example.appcinema.databinding.ActivityRoomBinding;
import com.example.appcinema.inteface.SlotListener;
import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity implements SlotListener {
    ActivityRoomBinding binding;
    SlotAdapter slotAdapter;
    RoomViewModel viewModel;
    Room choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RoomActivity.this,R.layout.activity_room);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        Intent intent = getIntent();
        choose = (Room) intent.getSerializableExtra("roomChose");
        obeserverViewModel();

        setListener();
    }

    private void obeserverViewModel() {
//        viewModel.obeRoom(choose);
        binding.rcChair.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,10,GridLayoutManager.VERTICAL,false);
        binding.rcChair.setLayoutManager(manager);
        binding.rcChair.setItemAnimator(new DefaultItemAnimator());
        slotAdapter = new SlotAdapter(choose.getListSlot(),RoomActivity.this);
        binding.rcChair.setAdapter(slotAdapter);
//        viewModel.getRoomSelect().observe(this, new Observer<Room>() {
//            @Override
//            public void onChanged(Room room) {
//
//            }
//        });



    }

    private void setListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Slot> select = slotAdapter.selectedList();
                viewModel.changeStatus(select,choose);
                slotAdapter.notifyDataSetChanged();


            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onSlotShowAction(Boolean isSelected) {
        if (isSelected){
            binding.lnBottom.setVisibility(View.VISIBLE);
        } else{
            binding.lnBottom.setVisibility(View.GONE);
        }

    }
}