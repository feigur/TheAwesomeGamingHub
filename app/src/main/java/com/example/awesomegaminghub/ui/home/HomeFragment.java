package com.example.awesomegaminghub.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awesomegaminghub.data.model.LoggedInUser;
import com.example.awesomegaminghub.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.example.awesomegaminghub.R;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LoggedInUser user;
    private SharedPreferences sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("loggedUser", "");
        user = gson.fromJson(json, LoggedInUser.class);

        final TextView userTextView = getActivity().findViewById(R.id.textView);
        userTextView.setText(user.getDisplayName());

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}