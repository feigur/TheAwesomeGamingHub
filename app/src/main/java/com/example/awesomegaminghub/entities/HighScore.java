package com.example.awesomegaminghub.entities;

import java.util.ArrayList;

public class HighScore {

    private long ID;
    private int lengthOfScore = 10;

    private  String username;

    private ArrayList<String> highscores;

    public HighScore(){
        this.highscores= new ArrayList<String>(5);
        for (int i = 0; i < lengthOfScore; i++) {
            highscores.add("N/A");
        }
    }

    public  HighScore(ArrayList<String> highscores){this.highscores = highscores;}

    public  ArrayList<String> addHighSchore(String msg){
        for(int i = lengthOfScore-1; i>0; i--){
            String temp = highscores.get(i-1);
            this.highscores.set(i,temp);
        }
        this.highscores.set(0,msg);
        return highscores;
    }

    public  ArrayList<String> getHighscores() { return highscores;}

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
}
