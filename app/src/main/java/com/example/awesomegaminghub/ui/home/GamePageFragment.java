package com.example.awesomegaminghub.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.MainUnityActivity;
import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.databinding.FragmentGamePageBinding;
import com.example.awesomegaminghub.databinding.FragmentLoginBinding;
import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.HighScore;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePageFragment extends Fragment {

    boolean isUnityLoaded = false;

    ActivityResultLauncher<Intent> gameActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                    }
                }
            });

    private FragmentGamePageBinding binding;
    private ArrayAdapter<String> highscoreAdapter;
    private ListView listView;
    private Button gameButton;
    private TextView chosenGame;
    private String gameName;
    private String gameId = "";

    private HighScore highScore;
    private int count;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGamePageBinding.inflate(inflater, container, false);
        highscoreAdapter = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1);

        listView = binding.highscoreList;
        listView.setAdapter(highscoreAdapter);

        gameButton = binding.playGameButton;
        chosenGame = binding.chosenGame;
        gameName = getArguments().getString("gameName");
        chosenGame.setText("You are playing: " + gameName);
        View root = binding.getRoot();
        getData();

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadUnity(v);
            }
        });
        return root;
    }

    @Override
    public void onResume () {
        super.onResume();
        getData();
    }

    private HighScore getHighScore(){
        if(gameName.equals("Orðle")){
            gameId = "1";
        }
        else if(gameName.equals("Minesweeper")){
            gameId = "2";
        }
        else if(gameName.equals("Lava Hazard")){
            gameId = "3";
        }
        else if(gameName.equals("Snákur")){
            gameId = "4";
        }
        return ((MainActivity)getActivity()).getHighScore(gameId);
    }

    private void getData(){
        int delay = 100;
        highScore = null;
        count = 0;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                highScore = getHighScore();
                if(count < 2){
                    count = count + 1;
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

    public void populateListView(){
        String buff;
        ArrayList<String> usernamesHighscore = highScore.getUsernamesHighscores();
        ArrayList<Integer> scoresHighscore = highScore.getScoresHighscores();
        highscoreAdapter.clear();
        for (int i = 0; i < usernamesHighscore.size();i++){
            buff = usernamesHighscore.get(i) + ": " + scoresHighscore.get(i).toString();
            highscoreAdapter.add(buff);
        }
    }

    public void btnLoadUnity(View v) {
        isUnityLoaded = true;
        Intent intent = new Intent(getActivity(), MainUnityActivity.class);
        intent.putExtra("Game Name", gameName);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("loggedUser", "");
        Account user = gson.fromJson(json, Account.class);
        intent.putExtra("Username", user.getUsername());
        //Intent intent = new Intent(this, MainUnityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        gameActivityResultLauncher.launch(intent);
        //startActivityForResult(intent, 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}