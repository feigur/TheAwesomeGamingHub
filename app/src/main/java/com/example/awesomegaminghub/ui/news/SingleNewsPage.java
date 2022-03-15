package com.example.awesomegaminghub.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.awesomegaminghub.R;

public class SingleNewsPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_news_page, container, false);

        TextView textView = view.findViewById(R.id.newsPageTextTest);
        textView.setText(getArguments().getString("newsName"));

        return view;

    }
}