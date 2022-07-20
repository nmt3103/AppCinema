package com.example.appcinema.activities;


import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityTicketDetailBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;


import java.util.Date;

public class TicketDetailActivity extends AppCompatActivity {

    ActivityTicketDetailBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TicketDetailActivity.this,R.layout.activity_ticket_detail);
        binding.setLifecycleOwner(this);

        Movie movie = new Movie(1,"How to train your dragon 2", R.drawable.teaser_ralph,R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",
                (float) 2.7,"1h 41min",
                "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph." +
                        "\n" +
                        "Ralph's goal was simple, wanting to win and get a medal to be considered a hero. " +
                        "But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex.",
                "_BcYBFC6zfY","ctVBmyub02A");
        Order order = new Order(1, movie,new Date(),"Ha noi Cinema 1",50000,"A3",R.drawable.qr);


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

        setListeners();
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