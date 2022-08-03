package com.example.appcinema.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityTicketDetailBinding;
import com.example.appcinema.model.Order;
import com.example.appcinema.viewmodel.CheckOutViewModel;
import com.example.appcinema.viewmodel.TicketDetailViewModel;
import com.squareup.picasso.Picasso;


public class TicketDetailActivity extends AppCompatActivity {

    ActivityTicketDetailBinding binding;
    TicketDetailViewModel ticketDetailViewModel;
    Order order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TicketDetailActivity.this,R.layout.activity_ticket_detail);
        binding.setLifecycleOwner(this);
        ticketDetailViewModel = new ViewModelProvider(this).get(TicketDetailViewModel.class);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        observerViewModel();
        setListeners();
    }

    private void observerViewModel() {
//        ticketDetailViewModel.getOrderLiveData().observe(this, new Observer<Order>() {
//            @Override
//            public void onChanged(Order order) {
//                Picasso.get().load(order.getMovie().getImgPoster()).into(binding.imgTicket);
////                binding.imgTicket.setImageResource(order.getMovie().getImgPoster());
//                binding.tvName.setText(order.getMovie().getName());
//                binding.rateBarMovie.setRating(order.getMovie().getRate());
//                binding.tvCate.setText(order.getMovie().getCate());
//                binding.tvTime.setText(order.getMovie().getTime());
//                binding.tvCinema.setText(order.getLocation());
//                binding.tvDateTime.setText(order.getDate().toString());
//                binding.tvSeat.setText(order.getSlot());
//                binding.tvPaid.setText(Integer.toString(order.getPrice()));
//
//                byte[] bytes = Base64.decode(order.getImgQr(),Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                binding.imgQr.setImageBitmap(bitmap);
//
//                binding.tvIdOrder.setText(order.getId());
//            }
//        });
                Picasso.get().load(order.getMovie().getImgPoster()).into(binding.imgTicket);
//                binding.imgTicket.setImageResource(order.getMovie().getImgPoster());
                binding.tvName.setText(order.getMovie().getName());
                binding.rateBarMovie.setRating(order.getMovie().getRate());
                binding.tvCate.setText(order.getMovie().getCate());
                binding.tvTime.setText(order.getMovie().getTime());
                binding.tvCinema.setText(order.getLocation());
                binding.tvDateTime.setText(order.getDate().toString());
                binding.tvSeat.setText(order.getSlot());
                binding.tvPaid.setText(Integer.toString(order.getPrice()));

                byte[] bytes = Base64.decode(order.getImgQr(),Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                binding.imgQr.setImageBitmap(bitmap);

                binding.tvIdOrder.setText(order.getId());

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