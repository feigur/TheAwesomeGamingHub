package com.example.awesomegaminghub.entities;

import java.util.ArrayList;

public class News {

    private long ID;
    private int lengthOfNews = 10;

    private  String username;

    private ArrayList<String> news;

    public News(){
        this.news= new ArrayList<String>(lengthOfNews);
        for (int i = 0; i < lengthOfNews; i++) {
            news.add("N/A");
        }
    }

    public  News(ArrayList<String> news){this.news = news;}

    public  ArrayList<String> addNews(String msg){
        for(int i = lengthOfNews-1; i>0; i--){
            String temp = news.get(i-1);
            this.news.set(i,temp);
        }
        this.news.set(0,msg);
        return news;
    }

    public  ArrayList<String> getNews() { return news;}

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
