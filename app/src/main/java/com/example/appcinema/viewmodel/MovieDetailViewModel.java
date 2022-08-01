package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<List<Actor>> listActorLiveData;
    private MutableLiveData<List<String>> listLinkVideoLiveData;
    private MutableLiveData<Movie> movieLiveData;
    private MutableLiveData<List<Integer>> listIdActorLiveData;
    private List<Actor> actors;
    private List<String> links;
    private List<Integer> listIdActor;


    public MovieDetailViewModel() {
        movieLiveData = new MutableLiveData<>();
        listActorLiveData = new MutableLiveData<>();
        listLinkVideoLiveData = new MutableLiveData<>();
        listIdActorLiveData = new MutableLiveData<>();
        listIdActor = new ArrayList<>();
        listIdActor.add(1);
        listIdActor.add(38);
        listIdActor.add(39);
        listIdActor.add(40);
        listIdActor.add(41);
        listIdActor.add(42);
        listIdActor.add(43);
//        initData();
    }

//    private void initData() {
//        getMovie();
//
//
//
//    }

    public void getMovie(Movie movie) {
//        movie.setListIdActor(listIdActor);
        movieLiveData.setValue(movie);
        getActor(movie);
        getLinkVideo(movie);
    }

    private void getActor(Movie movie) {
        List<Integer> listIdActorRead = new ArrayList<>();
        listIdActorRead = movie.getListIdActor();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("actors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            actors = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : snapshot){
                                for (int i = 0 ; i < listIdActor.size();i++){
                                    if (listIdActor.get(i) == Integer.parseInt(doc.get("id").toString())){
                                        Actor actor = new Actor();
                                        actor.setId(Integer.parseInt(doc.get("id").toString()));
                                        actor.setName(doc.get("name").toString());
                                        actor.setImg(doc.get("linkImg").toString());
                                        actors.add(actor);
                                    }
                                }
                            }
                            listActorLiveData.setValue(actors);
                        }
                    }
                });
    }

    private void getLinkVideo(Movie movie) {
        links = new ArrayList<>();
        links.add(movie.getLinkTrailer());
        links.add(movie.getLinkMusic());

        listLinkVideoLiveData.setValue(links);
    }

    public MutableLiveData<List<Actor>> getListActorLiveData() {
        return listActorLiveData;
    }

    public MutableLiveData<List<String>> getListLinkVideoLiveData() {
        return listLinkVideoLiveData;
    }

    public MutableLiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }
}
