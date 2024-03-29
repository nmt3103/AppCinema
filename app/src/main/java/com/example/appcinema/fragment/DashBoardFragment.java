package com.example.appcinema.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.activities.MovieDetailActivity;
import com.example.appcinema.adapter.MovieTrendAdapter;
import com.example.appcinema.databinding.FragmentDashboardBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.viewmodel.TrendViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DashBoardFragment extends Fragment implements MovieTrendAdapter.MovieTrendListener {
    MovieTrendAdapter movieTrendAdapter;
    FragmentDashboardBinding binding;
    View view;
    TrendViewModel trendViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);
        view = binding.getRoot();
        trendViewModel = new ViewModelProvider(this).get(TrendViewModel.class);
        observerViewModel();

        return view;
    }

    private void observerViewModel() {
        binding.rcTrendMovie.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcTrendMovie.setLayoutManager( linearLayoutManager);
        binding.rcTrendMovie.setItemAnimator(new DefaultItemAnimator());
        trendViewModel.getListMovieLiveData().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Movie top1 = movies.get(0);
                movies.remove(0);
                binding.tvTop1.setText(top1.getName());
                Picasso.get().load(top1.getImgBig()).into(binding.imgTop1);
                binding.imgTop1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra("movie",top1);
                        startActivity(intent);
                    }
                });
                movieTrendAdapter = new MovieTrendAdapter(movies,DashBoardFragment.this::selectedMovie);
                binding.rcTrendMovie.setAdapter(movieTrendAdapter);
            }
        });


    }


    @Override
    public void selectedMovie(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);
    }
}