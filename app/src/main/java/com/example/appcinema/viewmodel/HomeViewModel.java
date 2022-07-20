package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Category;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Promo;

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
        // ViewPager Home
        listNow = new ArrayList<>();
        listNow.add(new Movie(1,"How to train your dragon 2", R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        listNow.add(new Movie(2,"Ralph dap pha 2",R.drawable.movie_ralph,R.drawable.poster_ralph,"Hoat hinh,hanh dong",(float) 3,"Review 2"));
        listNow.add(new Movie(3,"Onward",R.drawable.movie_onward,R.drawable.poster_onward,"Hoat hinh,hanh dong",(float) 2.5,"Review 3"));


        listMovieNow.setValue(listNow);
        //

        // ComingSoon

        listComing = new ArrayList<>();
        listComing.add(new Movie(1,"How to train your dragon 2",R.drawable.movie_dragon,R.drawable.poster_dragon,"Hoat hinh,hanh dong", (float) 2.7,"Review 1"));
        listComing.add(new Movie(2,"frozen",R.drawable.movie_dragon,R.drawable.poster_frozen,"Hoat hinh,hanh dong", (float) 2.7,"Review 2"));
        listComing.add(new Movie(3,"onward",R.drawable.movie_dragon,R.drawable.poster_onward,"Hoat hinh,hanh dong", (float) 2.7,"Review 3"));
        listComing.add(new Movie(4,"ralph",R.drawable.movie_dragon,R.drawable.poster_ralph,"Hoat hinh,hanh dong", (float) 2.7,"Review 4"));
        listComing.add(new Movie(5,"scoob",R.drawable.movie_dragon,R.drawable.poster_scoob,"Hoat hinh,hanh dong", (float) 2.7,"Review 5"));
        listComing.add(new Movie(6,"spongebob",R.drawable.movie_dragon,R.drawable.poster_spongebob,"Hoat hinh,hanh dong", (float) 2.7,"Review 6"));

        listMovieComing.setValue(listComing);

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
