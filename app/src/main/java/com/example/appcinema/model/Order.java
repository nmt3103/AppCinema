package com.example.appcinema.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private String id;
    private Movie movie;
    private Room room;
    private String customerId;
    private String date;
    private String location;
    private int price;
    private String slot;
    private String imgQr;

    public Order() {
    }

    public Order(Movie movie,Room room, String date, String location, int price, String slot) {
        this.movie = movie;
        this.room= room;
        this.date = date;
        this.location = location;
        this.price = price;
        this.slot = slot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getImgQr() {
        return imgQr;
    }

    public void setImgQr(String imgQr) {
        this.imgQr = imgQr;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "movie=" + movie.getName() +
                ", room=" + room.getId() +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", slot='" + slot + '\'' +
                '}';
    }
}
