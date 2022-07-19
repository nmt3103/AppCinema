package com.example.appcinema.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.adapter.TicketAdapter;
import com.example.appcinema.databinding.FragmentMyticketBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyTicketFragment extends Fragment {


    TicketAdapter ticketAdapter;
    FragmentMyticketBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyticketBinding.inflate(inflater,container,false);

        View view = binding.getRoot();



        List<Movie> trendList = new ArrayList<>();
        trendList.add(new Movie(6,"Advenger: Endgame", R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));
        trendList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        trendList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        trendList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        trendList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        trendList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1, trendList.get(0),new Date(),"Ha noi Cinema 1",50000,"A1"));
        orderList.add(new Order(2, trendList.get(1),new Date(),"Ha noi Cinema 1",50000,"A2"));
        orderList.add(new Order(3, trendList.get(2),new Date(),"Ha noi Cinema 1",50000,"A3"));
        orderList.add(new Order(4, trendList.get(3),new Date(),"Ha noi Cinema 2",50000,"A4"));
        orderList.add(new Order(5, trendList.get(4),new Date(),"Ha noi Cinema 2",50000,"A5"));
        orderList.add(new Order(6, trendList.get(5),new Date(),"Ha noi Cinema 2",50000,"A6"));

        binding.rvMyTicket.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rvMyTicket.setLayoutManager( linearLayoutManager);
        binding.rvMyTicket.setItemAnimator(new DefaultItemAnimator());

        ticketAdapter = new TicketAdapter(getContext(),orderList);

        binding.rvMyTicket.setAdapter(ticketAdapter);

        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderList.add(new Order(1, trendList.get(0),new Date(),"Ha noi Cinema 1",50000,"A1"));
                orderList.add(new Order(2, trendList.get(1),new Date(),"Ha noi Cinema 1",50000,"A2"));
                orderList.add(new Order(3, trendList.get(2),new Date(),"Ha noi Cinema 1",50000,"A3"));
                orderList.add(new Order(4, trendList.get(3),new Date(),"Ha noi Cinema 2",50000,"A4"));
                orderList.add(new Order(5, trendList.get(4),new Date(),"Ha noi Cinema 2",50000,"A5"));
                orderList.add(new Order(6, trendList.get(5),new Date(),"Ha noi Cinema 2",50000,"A6"));

                ticketAdapter.notifyDataSetChanged();
            }
        });
        binding.btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();

                orderList.add(new Order(1, trendList.get(0),new Date(),"Ha noi Cinema 1",50000,"A1"));
                orderList.add(new Order(2, trendList.get(1),new Date(),"Ha noi Cinema 1",50000,"A2"));

                ticketAdapter.notifyDataSetChanged();
            }
        });
        binding.btnExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();

                orderList.add(new Order(3, trendList.get(2),new Date(),"Ha noi Cinema 1",50000,"A3"));
                orderList.add(new Order(4, trendList.get(3),new Date(),"Ha noi Cinema 2",50000,"A4"));
                orderList.add(new Order(5, trendList.get(4),new Date(),"Ha noi Cinema 2",50000,"A5"));
                orderList.add(new Order(6, trendList.get(5),new Date(),"Ha noi Cinema 2",50000,"A6"));

                ticketAdapter.notifyDataSetChanged();
            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}