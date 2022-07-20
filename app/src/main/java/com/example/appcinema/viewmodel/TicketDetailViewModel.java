package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;

import java.util.Date;

public class TicketDetailViewModel extends ViewModel {
    private MutableLiveData<Order> orderLiveData;
    private Order order;

    public TicketDetailViewModel() {
        orderLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        Movie movie = new Movie(1,"How to train your dragon 2", R.drawable.teaser_ralph,R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",
                (float) 2.7,"1h 41min",
                "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph." +
                        "\n" +
                        "Ralph's goal was simple, wanting to win and get a medal to be considered a hero. " +
                        "But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex.",
                "_BcYBFC6zfY","ctVBmyub02A");
        order = new Order(1, movie,new Date(),"Ha noi Cinema 1",50000,"A3",R.drawable.qr);
        orderLiveData.setValue(order);
    }

    public MutableLiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }
}
