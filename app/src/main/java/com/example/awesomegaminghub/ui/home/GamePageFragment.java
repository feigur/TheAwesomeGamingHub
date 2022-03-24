package com.example.awesomegaminghub.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.awesomegaminghub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_page, container, false);

        String[] userName = {"MasterGamer667","SuperMarioFortniteSkin","GODGAMERTHEGAMERGOD","[Username Censored]","Nonni"};
        String[] highscores = {"60000000000001","4186791912","10110110","1488","21"};

        //This might not be needed when server is working
        List<Map<String, String>> highscore = new ArrayList<>();
        for(int i=0; i< userName.length; i++) {
            Map<String, String> highscoredata = new HashMap<>(2);
            highscoredata.put("Line1", userName[i]);
            highscoredata.put("Line2", highscores[i]);
            highscore.add(highscoredata);
        }

        SimpleAdapter hsAdapter = new SimpleAdapter(getActivity(), highscore,
                android.R.layout.simple_list_item_2,
                new String[] {"Line1","Line2" },
                new int[] {android.R.id.text1,android.R.id.text2 }
        );

        ListView listView = view.findViewById(R.id.highscoreList);
        listView.setAdapter(hsAdapter);

        Button gameButton = view.findViewById(R.id.playGameButton);
        TextView chosenGame = view.findViewById(R.id.chosenGame);
        String newsId = getArguments().getString("gameName");
        chosenGame.setText("You are playing: " + newsId);

        return view;
    }
}