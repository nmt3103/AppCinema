package com.example.appcinema.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appcinema.R;
import com.example.appcinema.adapter.CategoryAdapter;
import com.example.appcinema.adapter.MovieComingAdapter;
import com.example.appcinema.adapter.MoviePagerAdapter;
import com.example.appcinema.adapter.PromoAdapter;
import com.example.appcinema.databinding.FragmentHomeBinding;
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    CategoryAdapter categoryAdapter;
    MovieComingAdapter movieAdapter;
    PromoAdapter promoAdapter;
    FragmentHomeBinding binding;
    Handler pagerHandler = new Handler();
    private PreferenceManager preferenceManager;

    Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container, false);
        View view = binding.getRoot();


        loadUserDetails();


        List<Category> cate = new ArrayList<>();
        cate.add(new Category(1,"All","Mota1"));
        cate.add(new Category(2,"Kinh Di","Mota2"));
        cate.add(new Category(3,"Phuu Luu","Mota3"));
        cate.add(new Category(4,"Hanh Dong","Mota4"));
        cate.add(new Category(5,"Tinh Cam","Mota5"));
        cate.add(new Category(6,"Trinh Tham","Mota6"));
        binding.rcCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.rcCategory.setLayoutManager(linearLayoutManager);
        binding.rcCategory.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryAdapter(cate, new CategoryAdapter.CateListener() {
            @Override
            public void onCateClick(View view, int position) {
                Toast.makeText(view.getContext(), cate.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.rcCategory.setAdapter(categoryAdapter);


        //ViewPager

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        movieList.add(new Movie(2,"Ralph dap pha 2",R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",(float) 3,"Review 2"));
        movieList.add(new Movie(3,"Onward",R.drawable.movie_onward,R.drawable.poster_onward,"Hoat hinh,hanh dong",(float) 2.5,"Review 3"));

        binding.vpMain.setAdapter(new MoviePagerAdapter(movieList, binding.vpMain));

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
                pagerHandler.postDelayed(pagerRunable,3000);

            }
        });

        //MovieComing

        List<Movie> comingList = new ArrayList<>();
        comingList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        comingList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        comingList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        comingList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        comingList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));
        comingList.add(new Movie(6,"spongebob",R.drawable.movie_dragon,R.drawable.poster_spongebob,"Hoat hinh,hanh dong", (float) 2.7,"Review 6"));

        binding.rcMovie.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerMovie = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.rcMovie.setLayoutManager(linearLayoutManagerMovie);
        binding.rcMovie.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieComingAdapter(comingList, new MovieComingAdapter.MovieComingListener() {
            @Override
            public void onMovieComingClick(View view, int position) {
                Toast.makeText(view.getContext(), "ahihi do ngoc " + position, Toast.LENGTH_SHORT).show();
            }
        });
        binding.rcMovie.setAdapter(movieAdapter);


        //Promo


        List<Promo> promoList = new ArrayList<>();
        promoList.add(new Promo(1,"Happy for THPTQG 2022","Discount for student born in 2004",50));
        promoList.add(new Promo(2,"Happy for THPTQG 2022","Discount for student born in 2004",45));
        promoList.add(new Promo(3,"Happy for THPTQG 2022","Discount for student born in 2004",55));
        binding.rcPromo.setHasFixedSize(true);

        LinearLayoutManager linearLayoutPromo = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcPromo.setLayoutManager(linearLayoutPromo);
        binding.rcPromo.setItemAnimator(new DefaultItemAnimator());
        promoAdapter = new PromoAdapter(promoList);
        binding.rcPromo.setAdapter(promoAdapter);

        // Inflate the layout for this fragment
        return view;
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
}