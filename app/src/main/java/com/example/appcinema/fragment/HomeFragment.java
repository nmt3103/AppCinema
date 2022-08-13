package com.example.appcinema.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appcinema.R;
import com.example.appcinema.activities.MovieDetailActivity;
import com.example.appcinema.activities.ProfileActivity;
import com.example.appcinema.adapter.CategoryAdapter;
import com.example.appcinema.adapter.MovieComingAdapter;
import com.example.appcinema.adapter.MoviePagerAdapter;
import com.example.appcinema.adapter.PromoAdapter;
import com.example.appcinema.adapter.ReviewAdapter;
import com.example.appcinema.databinding.FragmentHomeBinding;
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;
import com.example.appcinema.model.Review;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.HomeViewModel;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
    private MovieComingAdapter movieAdapter;
    private PromoAdapter promoAdapter;
    private MoviePagerAdapter moviePagerAdapter;
    private FragmentHomeBinding binding;
    Handler pagerHandler = new Handler();
    private PreferenceManager preferenceManager;
    private HomeViewModel homeViewModel;
    private View view;
    private ReviewAdapter reviewAdapter;
    private Timer timer;


    Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container, false);
        binding.setLifecycleOwner(this);
        view = binding.getRoot();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        loadUserDetails();
        observerViewModel();
        setListener();
        return view;
    }

    private void setListener() {
        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
    }

    private void observerViewModel() {
        homeViewModel.getListReviewLiveData().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter = new ReviewAdapter(context,reviews);
                binding.viewPagerReview.setAdapter(reviewAdapter);
                binding.circleIndicator.setViewPager(binding.viewPagerReview);
                reviewAdapter.registerDataSetObserver(binding.circleIndicator.getDataSetObserver());

                if (timer == null){
                    timer = new Timer();
                }

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                int currentItem = binding.viewPagerReview.getCurrentItem();
                                int totalItem = reviews.size() -1;
                                if (currentItem < totalItem){
                                    currentItem ++;
                                    binding.viewPagerReview.setCurrentItem(currentItem);
                                }else {
                                    binding.viewPagerReview.setCurrentItem(0);
                                }
                            }
                        });
                    }
                },500,3000);
            }
        });







        //comingsoon
        binding.rcMovie.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerMovie = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.rcMovie.setLayoutManager(linearLayoutManagerMovie);
        binding.rcMovie.setItemAnimator(new DefaultItemAnimator());


        homeViewModel.getListMovieComing().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter = new MovieComingAdapter(movies, new MovieComingAdapter.MovieComingListener() {
                    @Override
                    public void onClickMovie(Movie movie) {
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra("movie",movie);
                        startActivity(intent);
                    }
                });
                binding.rcMovie.setAdapter(movieAdapter);
            }
        });

        //now playing

        homeViewModel.getListMovieNow().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviePagerAdapter = new MoviePagerAdapter(movies, binding.vpMain, new MoviePagerAdapter.MoviePagerListener() {
                    @Override
                    public void onClickMovie(Movie movie) {
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra("movie",movie);
                        startActivity(intent);
                    }
                });
                binding.vpMain.setAdapter(moviePagerAdapter);

            }
        });

        binding.vpMain.setClipToPadding(false);
        binding.vpMain.setClipChildren(false);
        binding.vpMain.setOffscreenPageLimit(3);
        binding.vpMain.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        binding.vpMain.setPageTransformer(compositePageTransformer);
        binding.vpMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pagerHandler.removeCallbacks(pagerRunable);
                pagerHandler.postDelayed(pagerRunable,10000);

            }
        });

        //promo
        binding.rcPromo.setHasFixedSize(true);
        LinearLayoutManager linearLayoutPromo = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcPromo.setLayoutManager(linearLayoutPromo);
        binding.rcPromo.setItemAnimator(new DefaultItemAnimator());

        homeViewModel.getListPromo().observe(getViewLifecycleOwner(), new Observer<List<Promo>>() {
            @Override
            public void onChanged(List<Promo> promos) {
                promoAdapter = new PromoAdapter(promos);
                binding.rcPromo.setAdapter(promoAdapter);

            }
        });

    }

    public Runnable pagerRunable = new Runnable() {
        @Override
        public void run() {
            binding.vpMain.setCurrentItem(binding.vpMain.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        pagerHandler.removeCallbacks(pagerRunable);
    }

    @Override
    public void onResume() {
        super.onResume();
        pagerHandler.postDelayed(pagerRunable,3000);


    }


    private void loadUserDetails() {
        preferenceManager = new PreferenceManager(context);
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imgUser.setImageBitmap(bitmap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }
}