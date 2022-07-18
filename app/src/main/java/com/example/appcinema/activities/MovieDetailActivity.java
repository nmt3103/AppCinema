package com.example.appcinema.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appcinema.R;
import com.google.android.material.tabs.TabLayout;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView imgTrailer,imgPoster;
    TextView tvName,tvCate,tvTime;
    AppCompatRatingBar ratingBar;
    TabLayout tabLayout;
    RecyclerView rvCast;
    Button btnBook;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imgTrailer = findViewById(R.id.imgTrailer);
        imgPoster = findViewById(R.id.imgPoster);
        tvName = findViewById(R.id.tvName);
        tvCate = findViewById(R.id.tvCategory);
        tvTime = findViewById(R.id.tvTime);
        ratingBar = findViewById(R.id.rateBarDetail);
        tabLayout = findViewById(R.id.tab_layout);
        rvCast = findViewById(R.id.rvCast);
        btnBook = findViewById(R.id.btnBook);
        viewPager2 = findViewById(R.id.view_pager_detail);
    }
}