package com.example.appcinema.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.appcinema.viewmodel.MovieDetailViewModel;
import com.example.appcinema.viewmodel.TicketDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {


    ActorAdapter actorAdapter;
    ActivityMovieDetailBinding binding;
    MovieDetailViewModel movieDetailViewModel;
    Movie movieChoose;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MovieDetailActivity.this,R.layout.activity_movie_detail);
        binding.setLifecycleOwner(this);
        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            movieChoose = (Movie) intent.getSerializableExtra("movie");
            movieDetailViewModel.getMovie(movieChoose);
            setListeners();
            observerViewModel();
        } else {
            Toast.makeText(this, "Do not get data", Toast.LENGTH_SHORT).show();
        }


    }

    private void observerViewModel() {
        movieDetailViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Picasso.get().load(movie.getImgTeaster()).into(binding.imgTrailer);
                Picasso.get().load(movie.getImgPoster()).into(binding.imgPoster);
                binding.tvName.setText(movie.getName());
                binding.tvCategory.setText(movie.getCate());
                binding.tvTime.setText(movie.getTime());
                binding.rateBarDetail.setRating(movie.getRate());
                binding.tvDetailSynopsis1.setText(movie.getReview());
//                binding.tvDetailSynopsis1.setShowingLine(4);
//                binding.tvDetailSynopsis1.addShowMoreText("Read more");
//                binding.tvDetailSynopsis1.addShowLessText("Less");
//                binding.tvDetailSynopsis1.setShowMoreColor(Color.BLUE);
//                binding.tvDetailSynopsis1.setShowLessTextColor(Color.BLUE);
            }
        });

        binding.rvCast.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rvCast.setLayoutManager(linearLayoutManager);
        binding.rvCast.setItemAnimator(new DefaultItemAnimator());
        movieDetailViewModel.getListActorLiveData().observe(this, new Observer<List<Actor>>() {
            @Override
            public void onChanged(List<Actor> actors) {
                actorAdapter = new ActorAdapter(actors);
                binding.rvCast.setAdapter(actorAdapter);
            }
        });

        movieDetailViewModel.getListLinkVideoLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                binding.viewPagerDetail.setAdapter(new VideoPagerAdapter(strings,binding.viewPagerDetail));

            }
        });
        binding.viewPagerDetail.setClipToPadding(false);
        binding.viewPagerDetail.setClipChildren(false);
        binding.viewPagerDetail.setOffscreenPageLimit(3);
        binding.viewPagerDetail.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewPagerDetail.setPageTransformer(compositePageTransformer);

    }

    private void setListeners() {
        binding.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this,ChooseActivity.class);
                intent.putExtra("movieChoose",movieChoose);
                startActivity(intent);
//                finish();
            }
        });
    }
}