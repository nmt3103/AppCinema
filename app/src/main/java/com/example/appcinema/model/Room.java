package com.example.appcinema.model;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    private String date;
    private List<Slot> listSlot;
    private String time;


    public Room(String date,List<Slot> listSlot, String time) {
        this.date = date;
        this.listSlot = listSlot;
        this.time = time;

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
}
