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
    private Movie movie;
    private List<Actor> actors;
    private List<String> links;
    private List<Integer> listIdActor;


    public MovieDetailViewModel() {
        movieLiveData = new MutableLiveData<>();
        listActorLiveData = new MutableLiveData<>();
        listLinkVideoLiveData = new MutableLiveData<>();
        listIdActor = new ArrayList<>();
        listIdActor.add(1);
        listIdActor.add(2);
        listIdActor.add(3);
        listIdActor.add(4);
        listIdActor.add(5);
        initData();
    }

    private void initData() {
        getMovie();
        getActor();
        getLinkVideo();


    }

    private void getLinkVideo() {
        links = new ArrayList<>();
        links.add(movie.getLinkTrailer());
        links.add(movie.getLinkMusic());

        listLinkVideoLiveData.setValue(links);
    }

    private void getActor() {
        actors = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("actors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
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

    private void getMovie() {
        movie = new Movie(1,"How to train your dragon 2", R.drawable.teaser_ralph,R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",
                (float) 2.7,"1h 41min",
                "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph." +
                        "\n" +
                        "Ralph's goal was simple, wanting to win and get a medal to be considered a hero. " +
                        "But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex.",
                "_BcYBFC6zfY","ctVBmyub02A",listIdActor);

        movieLiveData.setValue(movie);
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
