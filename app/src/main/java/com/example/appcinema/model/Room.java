package com.example.appcinema.model;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    private int id;
    private String date;
    private List<Slot> listSlot;
    private String time;
    private int movieId;


    public Room(int id,String date,List<Slot> listSlot, String time,int movieId) {
        this.id = id;
        this.date = date;
        this.listSlot = listSlot;
        this.time = time;
        this.movieId = movieId;

    }

    public Room() {
    }

    public List<Slot> getListSlot() {
        return listSlot;
    }

    public void setListSlot(List<Slot> listSlot) {
        this.listSlot = listSlot;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
