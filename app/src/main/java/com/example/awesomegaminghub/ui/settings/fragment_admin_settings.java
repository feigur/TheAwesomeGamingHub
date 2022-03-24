package com.example.awesomegaminghub.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.awesomegaminghub.databinding.FragmentAdminSettingsBinding;

public class fragment_admin_settings extends Fragment {

    private FragmentAdminSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminSettingsViewModel galleryViewModel =
                new ViewModelProvider(this).get(AdminSettingsViewModel.class);

        binding = FragmentAdminSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textChat;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button addNewsButton = binding.addNews;
        final Button promoteUserButton = binding.promoteUser;
        final Button muteUserButton = binding.muteUser;
        final Button deleteUserButton = binding.deleteUser;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}