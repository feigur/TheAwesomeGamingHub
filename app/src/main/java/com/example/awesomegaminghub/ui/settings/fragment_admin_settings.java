package com.example.awesomegaminghub.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.databinding.FragmentAdminSettingsBinding;
import com.example.awesomegaminghub.entities.Account;
import com.google.gson.Gson;

import java.util.List;

public class fragment_admin_settings extends Fragment {

    private FragmentAdminSettingsBinding binding;
    private ArrayAdapter<String> usersListView;
    private List<Account> usersList;
    private int selectedUser;
    private Handler handler = new Handler();
    private Runnable runnable;
    private SharedPreferences sharedPref;
    private Account admin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getData();
        AdminSettingsViewModel galleryViewModel =
                new ViewModelProvider(this).get(AdminSettingsViewModel.class);

        binding = FragmentAdminSettingsBinding.inflate(inflater, container, false);
        usersListView = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1);
        binding.userListView.setAdapter(usersListView);
        View root = binding.getRoot();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("loggedUser", "");
        admin = gson.fromJson(json, Account.class);

        final TextView textView = binding.textChat;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button promoteUserButton = binding.promoteUser;
        final Button muteUserButton = binding.muteUser;
        final Button deleteUserButton = binding.deleteUser;
        promoteUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Account user = usersList.get(selectedUser);
                if(user.getAdmin()){
                    ((MainActivity)getActivity()).setNotAdmin(user.getUsername());
                }
                else{
                    ((MainActivity)getActivity()).setAdmin(user.getUsername());
                }
                getData();
            }
        });

        muteUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Account user = usersList.get(selectedUser);
                if(user.getMuted()){
                    ((MainActivity)getActivity()).setUnMuted(admin.getUsername(),user.getUsername());
                }
                else{
                    ((MainActivity)getActivity()).setMuted(admin.getUsername(),user.getUsername());
                }
                getData();
            }
        });
        binding.userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedUser = position;
                usersList = updateUserList();
                Account user = usersList.get(selectedUser);
                binding.textChat.setText(user.getUsername());
                if(user.getMuted()){
                    muteUserButton.setText("Unmute user");
                }
                else{
                    muteUserButton.setText("Mute user");
                }
                if(user.getAdmin()){
                    promoteUserButton.setText("Demote user");
                }
                else{
                    promoteUserButton.setText("Promote user");
                }
            }

        });

    }

    private void getData(){
        int delay = 100;
        usersList = null;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                usersList = updateUserList();
                if(usersList == null){
                    handler.postDelayed(runnable, delay);
                }
                else{
                    populateListView();
                    handler.removeCallbacks(runnable);
                }

            }
        }, delay);

        super.onResume();
    }

    public List<Account> updateUserList(){
        return ((MainActivity)getActivity()).getAccounts();
    }
    //Todo------------------------------------
    public void populateListView(){
        usersList = this.updateUserList();
        TextView textView;
        String buff;
        Account acc;
        usersListView.clear();
        for (int i = 0; i < usersList.size();i++){
            acc = usersList.get(i);
            buff = "Username: " + acc.getUsername()+" | Admin:";
            if(acc.getAdmin()){
                buff += " True ";
            }
            else {
                buff += "  False";
            }
            buff += " | Muted:";
            if(acc.getMuted()){
                buff += " True ";
            }
            else {
                buff += "  False";
            }
            usersListView.add(buff);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        usersList = null;
        binding = null;
    }
}