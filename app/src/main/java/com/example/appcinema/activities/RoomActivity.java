package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.appcinema.R;
import com.example.appcinema.adapter.SlotAdapter;
import com.example.appcinema.databinding.ActivityRoomBinding;
import com.example.appcinema.model.Slot;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    ActivityRoomBinding binding;
    SlotAdapter slotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RoomActivity.this,R.layout.activity_room);
        binding.setLifecycleOwner(this);

        binding.rcChair.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,10,GridLayoutManager.VERTICAL,false);
        binding.rcChair.setLayoutManager(manager);
        binding.rcChair.setItemAnimator(new DefaultItemAnimator());

        List<Slot> list = new ArrayList<>();
        list.add(new Slot(1,"A1",true));
        list.add(new Slot(2,"A2",true));
        list.add(new Slot(3,"A3",true));
        list.add(new Slot(4,"A4",true));
        list.add(new Slot(5,"A5",true));
        list.add(new Slot(6,"A6",true));
        list.add(new Slot(7,"A7",true));
        list.add(new Slot(8,"A8",true));
        list.add(new Slot(9,"A9",true));
        list.add(new Slot(55,"A10",true));

        list.add(new Slot(10,"B1",false));
        list.add(new Slot(11,"B2",false));
        list.add(new Slot(12,"B3",false));
        list.add(new Slot(13,"B4",false));
        list.add(new Slot(14,"B5",false));
        list.add(new Slot(15,"B6",false));
        list.add(new Slot(16,"B7",false));
        list.add(new Slot(17,"B8",false));
        list.add(new Slot(18,"B9",false));
        list.add(new Slot(56,"B10",false));

        list.add(new Slot(19,"C1",true));
        list.add(new Slot(20,"C2",true));
        list.add(new Slot(21,"C3",true));
        list.add(new Slot(22,"C4",true));
        list.add(new Slot(23,"C5",true));
        list.add(new Slot(24,"C6",true));
        list.add(new Slot(25,"C7",true));
        list.add(new Slot(26,"C8",true));
        list.add(new Slot(27,"C9",true));
        list.add(new Slot(57,"C10",true));

        list.add(new Slot(28,"D1",true));
        list.add(new Slot(29,"D2",true));
        list.add(new Slot(30,"D3",true));
        list.add(new Slot(31,"D4",true));
        list.add(new Slot(32,"D5",true));
        list.add(new Slot(33,"D6",true));
        list.add(new Slot(34,"D7",true));
        list.add(new Slot(35,"D8",true));
        list.add(new Slot(36,"D9",true));
        list.add(new Slot(58,"D10",true));


        list.add(new Slot(37,"E1",true));
        list.add(new Slot(38,"E2",true));
        list.add(new Slot(39,"E3",true));
        list.add(new Slot(40,"E4",true));
        list.add(new Slot(41,"E5",true));
        list.add(new Slot(42,"E6",true));
        list.add(new Slot(43,"E7",true));
        list.add(new Slot(44,"E8",true));
        list.add(new Slot(45,"E9",true));
        list.add(new Slot(59,"E10",true));

        list.add(new Slot(46,"F1",true));
        list.add(new Slot(47,"F2",true));
        list.add(new Slot(48,"F3",true));
        list.add(new Slot(49,"F4",true));
        list.add(new Slot(50,"F5",true));
        list.add(new Slot(51,"F6",true));
        list.add(new Slot(52,"F7",true));
        list.add(new Slot(53,"F8",true));
        list.add(new Slot(54,"F9",true));
        list.add(new Slot(60,"F10",true));

        list.add(new Slot(61,"G1",false));
        list.add(new Slot(62,"G2",false));
        list.add(new Slot(63,"G3",false));
        list.add(new Slot(64,"G4",false));
        list.add(new Slot(65,"G5",false));
        list.add(new Slot(66,"G6",false));
        list.add(new Slot(67,"G7",false));
        list.add(new Slot(68,"G8",false));
        list.add(new Slot(69,"G9",false));
        list.add(new Slot(70,"G10",false));

        slotAdapter = new SlotAdapter(list);
        binding.rcChair.setAdapter(slotAdapter);


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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