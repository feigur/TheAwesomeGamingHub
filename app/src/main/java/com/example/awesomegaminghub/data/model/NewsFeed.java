package com.example.awesomegaminghub.data.model;

/**
 * Data class that handles the newsfeed
 */
public class NewsFeed {
    private String newsTitle;
    private String newsText;

    public NewsFeed(String newsTitle, String newsText) {
        this.newsTitle = newsTitle;
        this.newsText = newsText;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }
}
