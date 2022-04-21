package com.example.awesomegaminghub.ui.userSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.databinding.FragmentAdminSettingsBinding;
import com.example.awesomegaminghub.databinding.FragmentUserSettingsBinding;
import com.example.awesomegaminghub.entities.Account;
import com.google.gson.Gson;


public class fragment_user_settings extends Fragment {

    private FragmentUserSettingsBinding binding;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private ImageView pic4;
    private ImageView pic5;
    private ImageView pic6;
    private ImageView pic7;
    private ImageView pic8;
    private ImageView pic9;

    private Handler handler = new Handler();
    private Runnable runnable;
    private int count;

    private Account user;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Button button;

    private TextView oldPass;
    private TextView newPass;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false);
        init();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = sharedPref.getString("loggedUser", "");
        user = gson.fromJson(json, Account.class);
        if(user.getAdmin()){
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

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = binding.getRoot().findViewById(R.id.buttonChangePass);
        oldPass = binding.getRoot().findViewById(R.id.oldPass);
        newPass = binding.getRoot().findViewById(R.id.newPass);
        newPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(newPass, 0);
                else
                    keyboard.hideSoftInputFromWindow(newPass.getWindowToken(), 0);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setPassword(user.getUsername(),oldPass.getText().toString(),newPass.getText().toString());
            }
        });

        pic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(1);
            }
        });
        pic2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(2);
            }
        });
        pic3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(3);
            }
        });
        pic4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(4);
            }
        });
        pic5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(5);
            }
        });
        pic6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(6);
            }
        });
        pic7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(7);
            }
        });
        pic8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(8);
            }
        });
        pic9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changePhoto(9);
            }
        });
    }

    private void changePhoto(Integer photoID){
        int delay = 100;
        count = 0;
        String username = user.getUsername();
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                user = ((MainActivity)getActivity()).setPhotoID(username,photoID);
                if(count < 2){
                    count = count + 1;
                    handler.postDelayed(runnable, delay);
                }
                else{
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putString("loggedUser",json);
                    editor.apply();
                    ((MainActivity)getActivity()).changeProfilePic();
                    handler.removeCallbacks(runnable);
                }

            }
        }, delay);

        super.onResume();
    }

    private void init(){
        pic1 = binding.profPic1;
        pic2 = binding.profPic2;
        pic3 = binding.profPic3;
        pic4 = binding.profPic4;
        pic5 = binding.profPic5;
        pic6 = binding.profPic6;
        pic7 = binding.profPic7;
        pic8 = binding.profPic8;
        pic9 = binding.profPic9;
        pic1.setImageResource(R.drawable.pic_1);
        pic2.setImageResource(R.drawable.pic_2);
        pic3.setImageResource(R.drawable.pic_3);
        pic4.setImageResource(R.drawable.pic_4);
        pic5.setImageResource(R.drawable.pic_5);
        pic6.setImageResource(R.drawable.pic_6);
        pic7.setImageResource(R.drawable.pic_7);
        pic8.setImageResource(R.drawable.pic_8);
        pic9.setImageResource(R.drawable.pic_9);
    }


}