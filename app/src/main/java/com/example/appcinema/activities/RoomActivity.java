package com.example.appcinema.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.appcinema.model.Movie;
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
    String location;
    List<Slot> selectList;
    Movie movieChoose;


    private final ActivityResultLauncher<Intent> confirm = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    if (intent != null){
                        if (intent.getBooleanExtra("IsSuccess", false)){
                            Toast.makeText(this, "Update Slot", Toast.LENGTH_SHORT).show();
                            List<Slot> select = slotAdapter.selectedList();
                             viewModel.changeStatus(select,choose,location);
                            startActivity(new Intent(RoomActivity.this,CheckOutDoneActivity.class));
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RoomActivity.this,R.layout.activity_room);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        Intent intent = getIntent();
        choose = (Room) intent.getSerializableExtra("roomChose");
        movieChoose = (Movie) intent.getSerializableExtra("movieChoose");
        location = intent.getStringExtra("location");
        binding.tvName.setText(movieChoose.getName());
        obeserverViewModel();

        setListener();
    }

    private void obeserverViewModel() {

        binding.tvDate.setText(choose.getDate());
        binding.tvTime.setText(choose.getTime());

        binding.rcChair.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,10,GridLayoutManager.VERTICAL,false);
        binding.rcChair.setLayoutManager(manager);
        binding.rcChair.setItemAnimator(new DefaultItemAnimator());
        slotAdapter = new SlotAdapter(choose.getListSlot(),RoomActivity.this);
        binding.rcChair.setAdapter(slotAdapter);

    }

    private void setListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoomActivity.this,CheckOutActivity.class);
                intent.putExtra("movieChoose",movieChoose);
                intent.putExtra("roomConfirm",choose);
                intent.putExtra("seatSelected",slotAdapter.getStringSeatLocation());
                intent.putExtra("numberSeat",slotAdapter.selectedList().size());
                intent.putExtra("location",location);
                confirm.launch(intent);

            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onSlotShowAction(Boolean isSelected) {
        if (isSelected){
            binding.lnBottom.setVisibility(View.VISIBLE);
        } else{
            binding.lnBottom.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onSLotClick() {
        binding.tvSlot.setText("Seat " + slotAdapter.getStringSeatLocation());
        binding.tvPrice.setText(slotAdapter.getTotalPrice());
    }


}