package com.example.awesomegaminghub.ui.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.databinding.FragmentChatBinding;
import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.Chat;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private Chat chatData;
    private ArrayAdapter<String> chatList;
    private EditText inputChat;
    private Button sendChat;
    private ListView viewChat;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Account chatUser;
    private SharedPreferences sharedPref;

    private static final String TAG = "ChatFragment: ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel galleryViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        chatList = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1);

        inputChat = binding.inputChat;
        sendChat = binding.buttonSendChat;
        viewChat = binding.chatView;
        viewChat.setAdapter(chatList);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("loggedUser", "");
        chatUser = gson.fromJson(json, Account.class);
        getData();
        if(chatUser.getAdmin()){
            ((MainActivity)getActivity()).setAdmin();
        }
        else{
            ((MainActivity)getActivity()).disableAdmin();
        }
        if(chatUser.getMuted()){
            ((MainActivity)getActivity()).disableChat();
        }
        else{
            ((MainActivity)getActivity()).enableChat();
        }

        inputChat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(inputChat, 0);
                else
                    keyboard.hideSoftInputFromWindow(inputChat.getWindowToken(), 0);
            }
        });

        sendChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String msg;
                msg = inputChat.getText().toString();
                if(!msg.equals("")){
                    ((MainActivity)getActivity()).sendChat(chatUser.getUsername(),msg);
                }
                inputChat.setText("");
            }
        });

        return root;
    }
    private Chat updateChatData(){
        return ((MainActivity)getActivity()).getChat(chatUser.getUsername());
    }

    public void populateListView(){
        TextView textView;
        String buff;
        Account acc;
        chatList.clear();
        for (int i = 0; i < chatData.getChat().size();i++){
            buff = chatData.getChat().get(i);
            if(buff != null){
                chatList.add(buff);
            }

        }
    }

    private void getData() {
        int delay = 100;
        chatData = null;
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                chatData = updateChatData();
                if (chatData == null) {
                    handler.postDelayed(runnable, delay);
                }
                else{
                    populateListView();
                    getDataContinous();
                }

            }
        }, delay);
    }

    private void getDataContinous() {
        int delay = 3000;
        chatData = null;
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                chatData = updateChatData();
                if (chatData != null) {
                    populateListView();
                }
                handler.postDelayed(runnable, delay);
            }
        }, delay);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}