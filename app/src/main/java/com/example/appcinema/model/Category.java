package com.example.appcinema.model;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private String mota;

    public Category() {
    }

    public Category(int id, String name, String mota) {
        this.id = id;
        this.name = name;
        this.mota = mota;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
