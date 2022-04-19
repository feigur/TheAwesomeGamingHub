package com.example.awesomegaminghub.entities;

import org.json.JSONArray;
import java.util.ArrayList;

public class Account {
    private long ID;

    private String username;
    private String password;
    private boolean admin;
    private boolean muted;
    private Integer photoID;


    public Account() {

    }

    public Account(String username, String password){
        this.username = username;
        this.password = password;
        this.admin = false;
        this.muted = false;
        this.photoID = 0;
    }


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhotoID(Integer photoID) { this.photoID = photoID;}

    public Integer getPhotoID() { return photoID;}



}
