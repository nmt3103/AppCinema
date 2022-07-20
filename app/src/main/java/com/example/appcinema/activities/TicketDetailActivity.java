package com.example.appcinema.activities;


import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityTicketDetailBinding;
import com.example.appcinema.model.Order;
import com.example.appcinema.viewmodel.TicketDetailViewModel;


public class TicketDetailActivity extends AppCompatActivity {

    ActivityTicketDetailBinding binding;
    TicketDetailViewModel ticketDetailViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TicketDetailActivity.this,R.layout.activity_ticket_detail);
        binding.setLifecycleOwner(this);
        ticketDetailViewModel = new ViewModelProvider(this).get(TicketDetailViewModel.class);

        observerViewModel();
        setListeners();
    }

    private void observerViewModel() {
        ticketDetailViewModel.getOrderLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                binding.imgTicket.setImageResource(order.getMovie().getImgPoster());
                binding.tvName.setText(order.getMovie().getName());
                binding.rateBarMovie.setRating(order.getMovie().getRate());
                binding.tvCate.setText(order.getMovie().getCate());
                binding.tvTime.setText(order.getMovie().getTime());
                binding.tvCinema.setText(order.getLocation());
                binding.tvDateTime.setText(order.getDate().toString());
                binding.tvSeat.setText(order.getSlot());
                binding.tvPaid.setText(Integer.toString(order.getPrice()));
                binding.imgQr.setImageResource(order.getImgQr());
                binding.tvIdOrder.setText(Integer.toString(order.getId()));
            }
        });

    }

    private void setListeners() {
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}