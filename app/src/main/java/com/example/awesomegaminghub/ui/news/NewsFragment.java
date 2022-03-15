package com.example.awesomegaminghub.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    //private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
        */

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        String[] newsFeedArray = {"news1","news2","n3ws","news4"};

        ListView listView = (ListView) view.findViewById(R.id.newsPage);
        final TextView textView = (TextView) view.findViewById(R.id.newsTextTest);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
          getActivity(),
          android.R.layout.simple_list_item_1,
          newsFeedArray
        );
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> aV, View view, int position, long id) {
                String selectedItem = (String) aV.getItemAtPosition(position);
                //textView.setText("I pressed " + selectedItem);
                Bundle bundle = new Bundle();
                bundle.putString("newsName", selectedItem);
                Navigation.findNavController(view).navigate(R.id.newsfeed_to_newspage, bundle);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}