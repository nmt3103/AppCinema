package com.example.appcinema.model;

public class Movie {
    private int id;
    private String name;
    private int imgBig;
    private int imgPoster;
    private String cate;
    private float rate;
    private String review;

    public Movie() {
    }

    public Movie(int id, String name, int imgBig, int imgPoster, String cate, float rate, String review) {
        this.id = id;
        this.name = name;
        this.imgBig = imgBig;
        this.imgPoster = imgPoster;
        this.cate = cate;
        this.rate = rate;
        this.review = review;
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

    public int getImgBig() {
        return imgBig;
    }

    public void setImgBig(int imgBig) {
        this.imgBig = imgBig;
    }

    public int getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(int imgPoster) {
        this.imgPoster = imgPoster;
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
}
