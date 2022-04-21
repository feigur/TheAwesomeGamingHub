package com.example.awesomegaminghub.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.databinding.FragmentNewsAddBinding;
import com.example.awesomegaminghub.entities.News;
import com.google.gson.Gson;

public class AddNewsPage extends Fragment {

    private EditText title;
    private EditText story;
    private FragmentNewsAddBinding binding;
    private Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsAddBinding.inflate(inflater, container, false);

        title = binding.getRoot().findViewById(R.id.inputTitle);
        story = binding.getRoot().findViewById(R.id.inputStory);
        submit = binding.getRoot().findViewById(R.id.submitNews);
        return binding.getRoot();
    }

}

