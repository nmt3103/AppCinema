package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;
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
    private MutableLiveData<List<Category>> listCate;
    private MutableLiveData<List<Promo>> listPromo;
    private List<Movie> listComing;
    private List<Movie> listNow;
    private List<Category> listC;
    private List<Promo> listP;

    public HomeViewModel() {
        listMovieComing = new MutableLiveData<>();
        listMovieNow = new MutableLiveData<>();
        listCate = new MutableLiveData<>();
        listPromo = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        listC = new ArrayList<>();
        //change in to read from firebase
        listC.add(new Category(1,"All","Mota1"));
        listC.add(new Category(2,"Kinh Di","Mota2"));
        listC.add(new Category(3,"Phuu Luu","Mota3"));
        listC.add(new Category(4,"Hanh Dong","Mota4"));
        listC.add(new Category(5,"Tinh Cam","Mota5"));
        listC.add(new Category(6,"Trinh Tham","Mota6"));


        listCate.setValue(listC);
        //


        // ComingSoon

        listComing = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            for (DocumentSnapshot doc : snapshot){
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
                                listComing.add(movie);
                            }
                            listMovieComing.postValue(listComing);
                        }
                    }
                });

        // ViewPager Home
        listNow = new ArrayList<>();
//        listNow.add(listComing.get(0));
//        listNow.add(listComing.get(1));
//        listNow.add(listComing.get(2));
        database.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            for (DocumentSnapshot doc : snapshot){
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

    public MutableLiveData<List<Category>> getListCate() {
        return listCate;
    }

    public MutableLiveData<List<Promo>> getListPromo() {
        return listPromo;
    }
}
