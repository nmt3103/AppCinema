package com.example.appcinema.model;

import java.io.Serializable;

public class User implements Serializable {
    public String name,image,email,password,id;

    public User() {
    }

    public User(String name, String image, String email, String password) {
        this.name = name;
        this.image = image;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
