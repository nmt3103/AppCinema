package com.example.appcinema.viewmodel;

import android.content.pm.ModuleInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.utilities.Constants;
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
        database.collection(Constants.KEY_COLLECTION_MOVIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            List<Movie> movieList = new ArrayList<>();
                            for (DocumentSnapshot doc : snapshot){
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get(Constants.KEY_MOVIES_ID).toString()));
                                movie.setImgBig(doc.get(Constants.KEY_MOVIES_IMGPAGER).toString());
                                movie.setImgPoster(doc.get(Constants.KEY_MOVIES_IMGPOSTER).toString());
                                movie.setImgTeaster(doc.get(Constants.KEY_MOVIES_IMGTRAILER).toString());
                                movie.setCate(doc.get(Constants.KEY_MOVIES_CATEGORY).toString());
                                movie.setLinkMusic(doc.get(Constants.KEY_MOVIES_LINKSONG).toString());
                                movie.setLinkTrailer(doc.get(Constants.KEY_MOVIES_LINKTRAILER).toString());
                                movie.setReview(doc.get(Constants.KEY_MOVIES_REVIEW).toString());
                                movie.setTime(doc.get(Constants.KEY_MOVIES_TIME).toString());
                                movie.setName(doc.get(Constants.KEY_MOVIES_NAME).toString());
                                movie.setRate(Float.parseFloat(doc.get(Constants.KEY_MOVIES_RATE).toString()));
                                List<Long> listActor = (List<Long>) doc.get(Constants.KEY_MOVIES_LISTACTOR);
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
