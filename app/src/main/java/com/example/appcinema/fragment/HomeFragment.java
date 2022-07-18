package com.example.appcinema.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView rcCategory,rcMovie,rcPromote;
    ImageView imgUser;
    SearchView searchView;
    ImageButton imageButton;
    CategoryAdapter categoryAdapter;
    MovieComingAdapter movieAdapter;
    PromoAdapter promoAdapter;

    TextView tvViewAll1,tvViewAll2,tvViewAll3;
    ViewPager2 viewPager2;
    Handler pagerHandler = new Handler();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        rcCategory = view.findViewById(R.id.rcCategory);
        rcMovie = view.findViewById(R.id.rcMovie);
        rcPromote = view.findViewById(R.id.rcPromo);
        imgUser = view.findViewById(R.id.imgUser);
        searchView = view.findViewById(R.id.search_view);
        imageButton = view.findViewById(R.id.btnSearch);
        viewPager2 = view.findViewById(R.id.vpMain);
        tvViewAll1 = view.findViewById(R.id.tvSeeAll1);
        tvViewAll2 = view.findViewById(R.id.tvSeeAll2);
        tvViewAll3 = view.findViewById(R.id.tvSeeAll3);


        List<Category> cate = new ArrayList<>();
        cate.add(new Category(1,"All","Mota1"));
        cate.add(new Category(2,"Kinh Di","Mota2"));
        cate.add(new Category(3,"Phuu Luu","Mota3"));
        cate.add(new Category(4,"Hanh Dong","Mota4"));
        cate.add(new Category(5,"Tinh Cam","Mota5"));
        cate.add(new Category(6,"Trinh Tham","Mota6"));
        rcCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        rcCategory.setLayoutManager(linearLayoutManager);
        rcCategory.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryAdapter(cate, new CategoryAdapter.CateListener() {
            @Override
            public void onCateClick(View view, int position) {
                Toast.makeText(view.getContext(), cate.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        rcCategory.setAdapter(categoryAdapter);


        //ViewPager

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        movieList.add(new Movie(2,"Ralph dap pha 2",R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",(float) 3,"Review 2"));
        movieList.add(new Movie(3,"Onward",R.drawable.movie_onward,R.drawable.poster_onward,"Hoat hinh,hanh dong",(float) 2.5,"Review 3"));

        viewPager2.setAdapter(new MoviePagerAdapter(movieList, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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

        rcMovie.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerMovie = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        rcMovie.setLayoutManager(linearLayoutManagerMovie);
        rcMovie.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieComingAdapter(comingList, new MovieComingAdapter.MovieComingListener() {
            @Override
            public void onMovieComingClick(View view, int position) {
                Toast.makeText(view.getContext(), "ahihi do ngoc " + position, Toast.LENGTH_SHORT).show();
            }
        });
        rcMovie.setAdapter(movieAdapter);


        //Promo


        List<Promo> promoList = new ArrayList<>();
        promoList.add(new Promo(1,"Happy for THPTQG 2022","Discount for student born in 2004",50));
        promoList.add(new Promo(2,"Happy for THPTQG 2022","Discount for student born in 2004",45));
        promoList.add(new Promo(3,"Happy for THPTQG 2022","Discount for student born in 2004",55));
        rcPromote.setHasFixedSize(true);

        LinearLayoutManager linearLayoutPromo = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        rcPromote.setLayoutManager(linearLayoutPromo);
        rcPromote.setItemAnimator(new DefaultItemAnimator());
        promoAdapter = new PromoAdapter(promoList);
        rcPromote.setAdapter(promoAdapter);








        // Inflate the layout for this fragment
        return view;
    }
    public Runnable pagerRunable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
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
}