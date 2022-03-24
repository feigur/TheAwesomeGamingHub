package com.example.awesomegaminghub.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.data.model.NewsFeed;

public class SingleNewsPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_news_page, container, false);

        //This will be added into the database later
        NewsFeed news1 = new NewsFeed("News Test","One day this will look amazing and everyone will applaud.");
        NewsFeed news2 = new NewsFeed("THEY PUT SUPER MARIO INTO FORTNITE???!??!?!","Too good to be true? Or a massive win for gamers?");
        NewsFeed n3ws = new NewsFeed("New game added, Solitaire!", "No need to use your dated old PC, play solitaire here! Now on your phone!");
        NewsFeed news4 = new NewsFeed("Running out of ideas","Listen, I'm having a hard time coming up with these funny news. I'm just not feeling it right now.");

        TextView newsTitle = view.findViewById(R.id.newsPageTitle);
        TextView newsText = view.findViewById(R.id.newsPageText);
        String newsId = getArguments().getString("newsName");

        //Populate the TextViews

        if(newsId == "news1") {
            newsTitle.setText(news1.getNewsTitle());
            newsText.setText(news1.getNewsText());
        } else if(newsId == "news2") {
            newsTitle.setText(news2.getNewsTitle());
            newsText.setText(news2.getNewsText());
        } else if(newsId == "n3ws") {
            newsTitle.setText(n3ws.getNewsTitle());
            newsText.setText(n3ws.getNewsText());
        } else if(newsId == "news4") {
            newsTitle.setText(news4.getNewsTitle());
            newsText.setText(news4.getNewsText());
        }

        return view;
    }

}