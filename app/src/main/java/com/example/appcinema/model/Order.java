package com.example.appcinema.model;

import java.util.Date;

public class Order {
    private int id;
    private Movie movie;
    private Date date;
    private String location;
    private int price;
    private String slot;
    private int imgQr;

    public Order() {
    }

    public Order(int id, Movie movie, Date date, String location, int price, String slot, int imgQr) {
        this.id = id;
        this.movie = movie;
        this.date = date;
        this.location = location;
        this.price = price;
        this.slot = slot;
        this.imgQr = imgQr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getImgQr() {
        return imgQr;
    }

    public void setImgQr(int imgQr) {
        this.imgQr = imgQr;
    }
}
