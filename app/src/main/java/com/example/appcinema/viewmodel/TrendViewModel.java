package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class TrendViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> listMovieLiveData;
    private List<Movie> movieList;

    public TrendViewModel() {
        listMovieLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        movieList = new ArrayList<>();

        movieList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        movieList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        movieList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        movieList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        movieList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));
        movieList.add(new Movie(6,"Advenger: Endgame", R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));

        listMovieLiveData.setValue(movieList);
    }

    public MutableLiveData<List<Movie>> getListMovieLiveData() {
        return listMovieLiveData;
    }
}
