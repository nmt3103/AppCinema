package com.example.appcinema.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.example.appcinema.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class TicketDetailActivity extends AppCompatActivity {
    AppCompatRatingBar ratingBar;
    ImageView img,imgUrl;
    RoundedImageView imgBack;
    TextView tvName,tvCate,tvTime,tvCinema,tvDate,tvSeat,tvPaid,tvIdOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        imgBack = findViewById(R.id.btnback);
        img = findViewById(R.id.imgTicket);
        tvName = findViewById(R.id.tvName);
        ratingBar = findViewById(R.id.rateBarMovie);
        tvCate = findViewById(R.id.tvCate);
        tvTime = findViewById(R.id.tvTime);
        tvCinema = findViewById(R.id.tvCinema);
        tvDate = findViewById(R.id.tvDateTime);
        tvSeat = findViewById(R.id.tvSeat);
        tvPaid = findViewById(R.id.tvPaid);
        imgUrl = findViewById(R.id.imgQr);
        tvIdOrder = findViewById(R.id.tvIdOrder);
    }
}