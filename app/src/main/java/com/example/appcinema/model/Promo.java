package com.example.appcinema.model;

public class Promo {
    private int id;
    private String title;
    private String mota;
    private int discount;

    public Promo() {
    }

    public Promo(int id, String title, String mota, int discount) {
        this.id = id;
        this.title = title;
        this.mota = mota;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
