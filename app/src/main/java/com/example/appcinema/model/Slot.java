package com.example.appcinema.model;

import java.io.Serializable;

public class Slot implements Serializable {
    private int id;
    private String name;
    private Boolean isSelect = false;
    private Boolean status;


    public Slot() {
    }

    public Slot(int id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }
}
