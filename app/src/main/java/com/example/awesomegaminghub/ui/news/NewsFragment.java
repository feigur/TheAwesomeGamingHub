package com.example.awesomegaminghub.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.databinding.FragmentNewsBinding;
import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.News;
import com.google.gson.Gson;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private News news;

    private Handler handler = new Handler();
    private Runnable runnable;
    private int count;

    private ArrayAdapter<String> listViewAdapter;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    private Button addNews;
    private Account user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        mSharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        getData();

        ListView listView = (ListView) binding.getRoot().findViewById(R.id.newsPage);

        listViewAdapter = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> aV, View view, int position, long id) {
                String selectedItem = (String) aV.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("newsName", selectedItem);
                Navigation.findNavController(view).navigate(R.id.newsfeed_to_newspage, bundle);
            }
        });
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("loggedUser", "");
        user = gson.fromJson(json, Account.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNews = binding.getRoot().findViewById(R.id.buttonAddNews);
        if(user.getAdmin()){
            addNews.setVisibility(View.VISIBLE);
        }
        else{
            addNews.setVisibility(View.GONE);
        }
    }

    private void getData(){
        int delay = 100;
        news = null;
        count = 0;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                news = ((MainActivity)getActivity()).getNews();
                if(count < 2){
                    count = count + 1;
                    handler.postDelayed(runnable, delay);
                }
                else{
                    Gson gson = new Gson();
                    String json = gson.toJson(news);
                    editor.putString("news",json);
                    editor.apply();
                    populateListView();
                    handler.removeCallbacks(runnable);
                }

            }
        }, delay);

        super.onResume();
    }

    public void populateListView(){
        TextView textView;
        String buff;
        Account acc;
        listViewAdapter.clear();
        for (int i = 0; i < news.getTitles().size();i++){
            buff = news.getTitles().get(i);
            listViewAdapter.add(buff);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}