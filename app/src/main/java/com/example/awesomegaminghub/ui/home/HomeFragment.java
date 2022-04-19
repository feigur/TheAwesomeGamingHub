package com.example.awesomegaminghub.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.data.model.LoggedInUser;
import com.example.awesomegaminghub.databinding.FragmentHomeBinding;
import com.example.awesomegaminghub.entities.Account;
import com.google.gson.Gson;
import com.example.awesomegaminghub.R;

import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Account user;
    private SharedPreferences sharedPref;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("loggedUser", "");
        user = gson.fromJson(json, Account.class);
        if(user.getAdmin()){
            homeViewModel.setText("admin");
            ((MainActivity)getActivity()).setAdmin();
        }
        else{
            ((MainActivity)getActivity()).disableAdmin();
        }
        if(user.getMuted()){
            ((MainActivity)getActivity()).disableChat();
        }
        else{
            ((MainActivity)getActivity()).enableChat();
        }
        final TextView userTextView = getActivity().findViewById(R.id.textView);
        userTextView.setText(user.getUsername());

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String[] gameArray = {"ordle","minesweeper","lavahazard"};

        ListView listView = (ListView) view.findViewById(R.id.gamePage);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                gameArray
        );
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> aV, View view, int position, long id) {
                String selectedItem = (String) aV.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("gameName", selectedItem);
                Navigation.findNavController(view).navigate(R.id.home_to_game, bundle);
            }
        });
        //return root;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}