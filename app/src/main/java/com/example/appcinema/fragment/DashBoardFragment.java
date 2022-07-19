package com.example.appcinema.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.adapter.MovieTrendAdapter;
import com.example.appcinema.databinding.FragmentDashboardBinding;
import com.example.appcinema.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DashBoardFragment extends Fragment {

//    TextView tvTop1;
//    ImageView imgTop1;
//    RecyclerView recyclerView;

    MovieTrendAdapter movieTrendAdapter;
    FragmentDashboardBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
//
//        tvTop1 = view.findViewById(R.id.tvTop1);
//        imgTop1 = view.findViewById(R.id.imgTop1);
//        recyclerView = view.findViewById(R.id.rcTrendMovie);


        List<Movie> trendList = new ArrayList<>();
        trendList.add(new Movie(6,"Advenger: Endgame",R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));
        trendList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        trendList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        trendList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        trendList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        trendList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));


        Collections.sort(trendList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Float.compare(o2.getRate(),o1.getRate());
            }
        });

        Movie top1 = trendList.get(0);
        trendList.remove(0);
        binding.tvTop1.setText(top1.getName());
        binding.imgTop1.setImageResource(top1.getImgBig());

        binding.rcTrendMovie.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcTrendMovie.setLayoutManager( linearLayoutManager);
        binding.rcTrendMovie.setItemAnimator(new DefaultItemAnimator());

        movieTrendAdapter = new MovieTrendAdapter(getContext(),trendList);
        binding.rcTrendMovie.setAdapter(movieTrendAdapter);



        return view;
    }
}