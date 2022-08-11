package com.example.appcinema.model;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    private Long id;
    private String date;
    private List<Slot> listSlot;
    private String time;
    private Long movieId;


    public Room(Long id,String date,List<Slot> listSlot, String time,Long movieId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
