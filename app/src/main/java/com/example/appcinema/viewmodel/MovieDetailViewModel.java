package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<List<Actor>> listActorLiveData;
    private MutableLiveData<List<String>> listLinkVideoLiveData;
    private MutableLiveData<Movie> movieLiveData;
    private Movie movie;
    private List<Actor> actors;
    private List<String> links;

    public MovieDetailViewModel() {
        movieLiveData = new MutableLiveData<>();
        listActorLiveData = new MutableLiveData<>();
        listLinkVideoLiveData = new MutableLiveData<>();
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
        actors.add(new Actor(1,"Reilly",R.drawable.cast1));
        actors.add(new Actor(2,"Silverman",R.drawable.cast2));
        actors.add(new Actor(3,"McBayer",R.drawable.cast3));
        actors.add(new Actor(4,"Henson",R.drawable.cast4));
        actors.add(new Actor(5,"Moore",R.drawable.cast5));

        listActorLiveData.setValue(actors);
    }

    private void getMovie() {
        movie = new Movie(1,"How to train your dragon 2", R.drawable.teaser_ralph,R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",
                (float) 2.7,"1h 41min",
                "Wreck-It Ralph wants to be loved by many people like his kind friend, Fix-It Felix. But no one likes evil characters like Ralph." +
                        "\n" +
                        "Ralph's goal was simple, wanting to win and get a medal to be considered a hero. " +
                        "But without realizing Ralph instead paved the way for criminals who can kill all the games in the game complex.",
                "_BcYBFC6zfY","ctVBmyub02A");

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
