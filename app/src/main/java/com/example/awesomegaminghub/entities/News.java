package com.example.awesomegaminghub.entities;

import java.util.ArrayList;

public class News {

    private long ID;
    private int lengthOfNews = 10;

    private ArrayList<String> news;
    private ArrayList<String> titles;
    private ArrayList<String> writers;

    public News(){
        this.ID = 2;
        this.news= new ArrayList<String>(lengthOfNews);
        this.titles = new ArrayList<>(lengthOfNews);
        this.writers = new ArrayList<>(lengthOfNews);
        for (int i = 0; i < lengthOfNews; i++) {
            news.add("N/A");
            titles.add("N/A");
            writers.add("N/A");
        }
    }

    public  News(ArrayList<String> news){this.news = news;}

    public  void addNews(String title, String story,String writer){
        for(int i = lengthOfNews-1; i>0; i--){
            String temp = this.news.get(i-1);
            this.news.set(i,temp);
            temp = this.titles.get(i-1);
            this.titles.set(i,temp);
            temp = this.writers.get(i-1);
            this.writers.set(i,temp);
        }
        this.news.set(0,story);
        this.titles.set(0,title);
        this.writers.set(0,writer);
    }

    public  ArrayList<String> getNews() { return news;}
    public  ArrayList<String> getTitles() { return titles;}
    public  ArrayList<String> getWriters() { return writers;}

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

}
