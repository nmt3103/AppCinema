package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;
import com.example.appcinema.model.Review;
import com.example.appcinema.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> listMovieComing;
    private MutableLiveData<List<Movie>> listMovieNow;
    private MutableLiveData<List<Promo>> listPromo;
    private MutableLiveData<List<Review>> listReviewLiveData;
    private List<Movie> listComing;
    private List<Movie> listNow;
    private List<Promo> listP;
    private List<Review> listReview;

    public HomeViewModel() {
        listReviewLiveData = new MutableLiveData<>();
        listMovieComing = new MutableLiveData<>();
        listMovieNow = new MutableLiveData<>();
        listPromo = new MutableLiveData<>();
        initData();
    }

    private void initData() {

        //Review
        listReview = new ArrayList<>();
        listReview.add(new Review(R.drawable.pic1));
        listReview.add(new Review(R.drawable.pic2));
        listReview.add(new Review(R.drawable.pic3));
        listReview.add(new Review(R.drawable.pic4));

        listReviewLiveData.postValue(listReview);



        // ComingSoon

        listComing = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_MOVIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
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
                                listComing.add(movie);
                            }
                            listMovieComing.postValue(listComing);
                        }
                    }
                });

        // ViewPager Home
        listNow = new ArrayList<>();
        database.collection(Constants.KEY_COLLECTION_MOVIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
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
                                listNow.add(movie);
                            }
                            listMovieNow.postValue(listNow);
                        }
                    }
                });
//
//        listMovieNow.setValue(listNow);
        //

        //Promo

        listP = new ArrayList<>();
        listP.add(new Promo(1,"Happy for THPTQG 2022","Discount for student born in 2004",50));
        listP.add(new Promo(2,"Happy for THPTQG 2022","Discount for student born in 2004",45));
        listP.add(new Promo(3,"Happy for THPTQG 2022","Discount for student born in 2004",55));

        listPromo.setValue(listP);



    }

    public MutableLiveData<List<Movie>> getListMovieComing() {
        return listMovieComing;
    }

    public MutableLiveData<List<Movie>> getListMovieNow() {
        return listMovieNow;
    }

    public MutableLiveData<List<Review>> getListReviewLiveData() {
        return listReviewLiveData;
    }

    public MutableLiveData<List<Promo>> getListPromo() {
        return listPromo;
    }
}
