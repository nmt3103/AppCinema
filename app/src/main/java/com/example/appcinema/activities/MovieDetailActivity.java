package com.example.appcinema.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appcinema.R;
import com.example.appcinema.adapter.ActorAdapter;
import com.example.appcinema.adapter.VideoPagerAdapter;
import com.example.appcinema.databinding.ActivityMovieDetailBinding;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Movie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView imgTrailer,imgPoster;
    TextView tvName,tvCate,tvTime,tvReadmore,tvDetail;
    AppCompatRatingBar ratingBar;
    RecyclerView rvCast;
    Button btnBook;
    ViewPager2 viewPager2;
    ActorAdapter actorAdapter;
    ActivityMovieDetailBinding binding;


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
        rvCast = findViewById(R.id.rvCast);
        btnBook = findViewById(R.id.btnBook);
        viewPager2 = findViewById(R.id.view_pager_detail);
        tvReadmore = findViewById(R.id.tvReadmore);
        tvDetail = findViewById(R.id.tvDetailSynopsis1);

        Movie movieIn = new Movie(1,"How to train your dragon 2",R.drawable.teaser_ralph,R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",
                (float) 2.7,"1h 41min",
                "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph." +
                        "\n" +
                        "Ralph's goal was simple, wanting to win and get a medal to be considered a hero. " +
                        "But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex.",
                "_BcYBFC6zfY","ctVBmyub02A");
        imgTrailer.setImageResource(movieIn.getImgTeaster());
        imgPoster.setImageResource(movieIn.getImgPoster());
        tvName.setText(movieIn.getName());
        tvCate.setText(movieIn.getCate());
        tvTime.setText(movieIn.getTime());
        ratingBar.setRating(movieIn.getRate());
        tvDetail.setText(movieIn.getReview());


        List<Actor> list = new ArrayList<>();
        list.add(new Actor(1,"Reilly",R.drawable.cast1));
        list.add(new Actor(2,"Silverman",R.drawable.cast2));
        list.add(new Actor(3,"McBayer",R.drawable.cast3));
        list.add(new Actor(4,"Henson",R.drawable.cast4));
        list.add(new Actor(5,"Moore",R.drawable.cast5));

        rvCast.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvCast.setLayoutManager(linearLayoutManager);
        rvCast.setItemAnimator(new DefaultItemAnimator());
        actorAdapter = new ActorAdapter(list);
        rvCast.setAdapter(actorAdapter);

        List<String> link = new ArrayList<>();
        link.add(movieIn.getLinkTrailer());
        link.add(movieIn.getLinkMusic());

        viewPager2.setAdapter(new VideoPagerAdapter(link,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        viewPager2.setPageTransformer(compositePageTransformer);



    }
}