package com.example.appcinema.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private int id;
    private String name;
    private String imgTeaster;
    private String imgBig;
    private String imgPoster;
    private String cate;
    private float rate;
    private String time;
    private String review;
    private String linkTrailer;
    private String linkMusic;
    private List<Integer> listIdActor;
        //
    public Movie() {
    }

    public Movie(int id, String name, String imgTeaster, String imgBig, String imgPoster, String cate, float rate, String time, String review, String linkTrailer, String linkMusic, List<Integer> listIdActor) {
        this.id = id;
        this.name = name;
        this.imgTeaster = imgTeaster;
        this.imgBig = imgBig;
        this.imgPoster = imgPoster;
        this.cate = cate;
        this.rate = rate;
        this.time = time;
        this.review = review;
        this.linkTrailer = linkTrailer;
        this.linkMusic = linkMusic;
        this.listIdActor = listIdActor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getLinkMusic() {
        return linkMusic;
    }

    public void setLinkMusic(String linkMusic) {
        this.linkMusic = linkMusic;
    }

    public List<Integer> getListIdActor() {
        return listIdActor;
    }

    public void setListIdActor(List<Integer> listIdActor) {
        this.listIdActor = listIdActor;
    }

    public String getImgTeaster() {
        return imgTeaster;
    }

    public void setImgTeaster(String imgTeaster) {
        this.imgTeaster = imgTeaster;
    }

    public String getImgBig() {
        return imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }

    public String getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(String imgPoster) {
        this.imgPoster = imgPoster;
    }
}
