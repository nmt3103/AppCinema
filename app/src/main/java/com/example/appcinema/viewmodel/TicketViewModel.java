package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketViewModel extends ViewModel {
    private MutableLiveData<List<Order>> listOrderLiveDate;
    private MutableLiveData<List<Order>> listNewOrder;
    private MutableLiveData<List<Order>> listExpireOrder;

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


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            List<Order> listExprie = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : snapshot){
                                List<Integer> listActor = new ArrayList<>();
                                listActor.add(1);
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get("id").toString()));
                                movie.setImgBig(doc.get("imgPager").toString());
                                movie.setImgPoster(doc.get("imgPoster").toString());
                                movie.setImgTeaster(doc.get("imgTrailer").toString());
                                movie.setCate(doc.get("Category").toString());
                                movie.setLinkMusic(doc.get("LinkSong").toString());
                                movie.setLinkTrailer(doc.get("LinkTrailer").toString());
                                movie.setListIdActor(listActor);
                                movie.setReview(doc.get("Review").toString());
                                movie.setTime(doc.get("Time").toString());
                                movie.setName(doc.get("name").toString());
                                movie.setRate(Float.parseFloat(doc.get("rate").toString()));
                                Format dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());
                                Order order = new Order(1, movie,dateFormat.format(new Date()).toString(),"Ha noi Cinema 3",50000,"A1",R.drawable.qr);
                                listExprie.add(order);
                            }
                            listExpireOrder.setValue(listExprie);
                        }
                    }
                });
    }

    private void getNewOrder() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            List<Order> listNew = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : snapshot){
                                List<Integer> listActor = new ArrayList<>();
                                listActor.add(1);
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get("id").toString()));
                                movie.setImgBig(doc.get("imgPager").toString());
                                movie.setImgPoster(doc.get("imgPoster").toString());
                                movie.setImgTeaster(doc.get("imgTrailer").toString());
                                movie.setCate(doc.get("Category").toString());
                                movie.setLinkMusic(doc.get("LinkSong").toString());
                                movie.setLinkTrailer(doc.get("LinkTrailer").toString());
                                movie.setListIdActor(listActor);
                                movie.setReview(doc.get("Review").toString());
                                movie.setTime(doc.get("Time").toString());
                                movie.setName(doc.get("name").toString());
                                movie.setRate(Float.parseFloat(doc.get("rate").toString()));
                                Format dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());
                                Order order = new Order(1, movie,dateFormat.format(new Date()).toString(),"Ha noi Cinema 2",50000,"A1",R.drawable.qr);
                                listNew.add(order);
                            }
                            listNewOrder.setValue(listNew);
                        }
                    }
                });
    }

    private void getAllOrder() {

//        List<Movie> movies = new ArrayList<>();

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            List<Order> listAll = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : snapshot){
                                List<Integer> listActor = new ArrayList<>();
                                listActor.add(1);
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get("id").toString()));
                                movie.setImgBig(doc.get("imgPager").toString());
                                movie.setImgPoster(doc.get("imgPoster").toString());
                                movie.setImgTeaster(doc.get("imgTrailer").toString());
                                movie.setCate(doc.get("Category").toString());
                                movie.setLinkMusic(doc.get("LinkSong").toString());
                                movie.setLinkTrailer(doc.get("LinkTrailer").toString());
                                movie.setListIdActor(listActor);
                                movie.setReview(doc.get("Review").toString());
                                movie.setTime(doc.get("Time").toString());
                                movie.setName(doc.get("name").toString());
                                movie.setRate(Float.parseFloat(doc.get("rate").toString()));
                                Format dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());

                                Order order = new Order(1, movie,dateFormat.format(new Date()).toString(),"Ha noi Cinema 1",50000,"A1",R.drawable.qr);
                                listAll.add(order);
                            }
                            listOrderLiveDate.setValue(listAll);
                        }
                    }
                });

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
