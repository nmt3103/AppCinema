package com.example.appcinema.viewmodel;

import android.content.pm.ModuleInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrendViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> listMovieLiveData;


    public TrendViewModel() {
        listMovieLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            List<Movie> movieList = new ArrayList<>();
                            for (DocumentSnapshot doc : snapshot){
//                                List<Integer> listActor = new ArrayList<>();
//                                listActor.add(1);
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get("id").toString()));
                                movie.setImgBig(doc.get("imgPager").toString());
                                movie.setImgPoster(doc.get("imgPoster").toString());
                                movie.setImgTeaster(doc.get("imgTrailer").toString());
                                movie.setCate(doc.get("Category").toString());
                                movie.setLinkMusic(doc.get("LinkSong").toString());
                                movie.setLinkTrailer(doc.get("LinkTrailer").toString());
                                movie.setReview(doc.get("Review").toString());
                                movie.setTime(doc.get("Time").toString());
                                movie.setName(doc.get("name").toString());
                                movie.setRate(Float.parseFloat(doc.get("rate").toString()));
                                List<Integer> listActor = (List<Integer>) doc.get("ListActor");
                                movie.setListIdActor(listActor);
                                movieList.add(movie);
                            }

                            Collections.sort(movieList, new Comparator<Movie>() {
                                @Override
                                public int compare(Movie o1, Movie o2) {
                                    return Float.compare(o2.getRate(),o1.getRate());
                                }
                            });
                            listMovieLiveData.setValue(movieList);
                        }
                    }
                });
    }

    public MutableLiveData<List<Movie>> getListMovieLiveData() {
        return listMovieLiveData;
    }
}
