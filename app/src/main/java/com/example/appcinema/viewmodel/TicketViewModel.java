package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketViewModel extends ViewModel {
    private MutableLiveData<List<Order>> listOrderLiveDate;
    private MutableLiveData<List<Order>> listNewOrder;
    private MutableLiveData<List<Order>> listExpireOrder;
    private List<Order> listAll,listNew,listExprie;

    public TicketViewModel() {
        listOrderLiveDate = new MutableLiveData<>();
        listNewOrder = new MutableLiveData<>();
        listExpireOrder = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        getAllOrder();
        getNewOrder();
        getExpireOrder();
    }

    private void getExpireOrder() {
        List<Movie> trendList = new ArrayList<>();
        trendList.add(new Movie(6,"Advenger: Endgame", R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));
        trendList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        trendList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        trendList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        trendList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        trendList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));

        listExprie = new ArrayList<>();
        listExprie.add(new Order(3, trendList.get(2),new Date(),"Ha noi Cinema 1",50000,"A3",R.drawable.qr));
        listExprie.add(new Order(4, trendList.get(3),new Date(),"Ha noi Cinema 2",50000,"A4",R.drawable.qr));
        listExprie.add(new Order(5, trendList.get(4),new Date(),"Ha noi Cinema 2",50000,"A5",R.drawable.qr));
        listExprie.add(new Order(6, trendList.get(5),new Date(),"Ha noi Cinema 2",50000,"A6",R.drawable.qr));

        listExpireOrder.setValue(listExprie);

    }

    private void getNewOrder() {
        List<Movie> trendList = new ArrayList<>();
        trendList.add(new Movie(6,"Advenger: Endgame", R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));
        trendList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        trendList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        trendList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        trendList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        trendList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));

        listNew = new ArrayList<>();
        listNew.add(new Order(1, trendList.get(0),new Date(),"Ha noi Cinema 1",50000,"A1",R.drawable.qr));
        listNew.add(new Order(2, trendList.get(1),new Date(),"Ha noi Cinema 1",50000,"A2",R.drawable.qr));

        listNewOrder.setValue(listNew);
    }

    private void getAllOrder() {

        List<Movie> trendList = new ArrayList<>();
        trendList.add(new Movie(6,"Advenger: Endgame", R.drawable.movie_endgame,R.drawable.endgame_poster,"Hoat hinh,hanh dong", (float) 5.0,"Review 6"));
        trendList.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        trendList.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        trendList.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        trendList.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        trendList.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));

        listAll = new ArrayList<>();
        listAll.add(new Order(1, trendList.get(0),new Date(),"Ha noi Cinema 1",50000,"A1",R.drawable.qr));
        listAll.add(new Order(2, trendList.get(1),new Date(),"Ha noi Cinema 1",50000,"A2",R.drawable.qr));
        listAll.add(new Order(3, trendList.get(2),new Date(),"Ha noi Cinema 1",50000,"A3",R.drawable.qr));
        listAll.add(new Order(4, trendList.get(3),new Date(),"Ha noi Cinema 2",50000,"A4",R.drawable.qr));
        listAll.add(new Order(5, trendList.get(4),new Date(),"Ha noi Cinema 2",50000,"A5",R.drawable.qr));
        listAll.add(new Order(6, trendList.get(5),new Date(),"Ha noi Cinema 2",50000,"A6",R.drawable.qr));

        listOrderLiveDate.setValue(listAll);

    }

    public MutableLiveData<List<Order>> getListOrderLiveDate() {
        return listOrderLiveDate;
    }

    public MutableLiveData<List<Order>> getListNewOrder() {
        return listNewOrder;
    }

    public MutableLiveData<List<Order>> getListExpireOrder() {
        return listExpireOrder;
    }
}
