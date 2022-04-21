package com.example.awesomegaminghub.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.data.model.NewsFeed;
import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.News;
import com.google.gson.Gson;

public class SingleNewsPage extends Fragment {

    private News news;
    private SharedPreferences sharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_news_page, container, false);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString("news", "");
        news = gson.fromJson(json, News.class);
        TextView newsTitle = view.findViewById(R.id.newsPageTitle);
        TextView newsText = view.findViewById(R.id.newsPageText);
        String newsId = getArguments().getString("newsName");

        //Populate the TextViews
        for (int i = 0; i < news.getTitles().size();i++)
            if(news.getTitles().get(i).equals(newsId)){
                newsTitle.setText(news.getTitles().get(i));
                newsText.setText(news.getNews().get(i));
            }


        return view;
    }

}