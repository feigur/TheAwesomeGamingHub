package com.example.awesomegaminghub.entities;

import java.util.ArrayList;

public class HighScore {

    private Integer ID;
    private int lengthOfScore = 10;

    private  String GameName;

    private ArrayList<String> usernamesHighscores;
    private ArrayList<Integer> scoresHighscores;

    public HighScore(){

    }

    public HighScore(Integer gameId, String gamename){
        this.ID = gameId;
        this.GameName = gamename;
        this.usernamesHighscores = new ArrayList<String>(lengthOfScore);
        this.scoresHighscores = new ArrayList<Integer>(lengthOfScore);
        for (int i = 0; i < lengthOfScore; i++) {
            usernamesHighscores.add("N/A");
            scoresHighscores.add(0);
        }
    }

    public  HighScore(ArrayList<String> usernamesHighscores, ArrayList<Integer> scoresHighscores){
        this.usernamesHighscores = usernamesHighscores;
        this.scoresHighscores = scoresHighscores;
    }

    public  void addHighSchore(String username, Integer score){
        boolean found = false;
        Integer tempScore = 0;
        Integer tempScore2;
        String tempUsername = "N/A";
        String tempUsername2;
        for(int i = 0; i<lengthOfScore; i++){
            if(found){
                tempScore2 = scoresHighscores.get(i);
                tempUsername2 = usernamesHighscores.get(i);
                scoresHighscores.set(i,tempScore);
                usernamesHighscores.set(i,tempUsername);
                tempScore = tempScore2;
                tempUsername = tempUsername2;
            }
            else if(score > scoresHighscores.get(i)){
                tempScore = scoresHighscores.get(i);
                tempUsername = usernamesHighscores.get(i);
                scoresHighscores.set(i,score);
                usernamesHighscores.set(i,username);
                found = true;
            }
        }
    }

    public ArrayList<Integer> getScoresHighscores() { return scoresHighscores;}
    public ArrayList<String> getUsernamesHighscores(){ return usernamesHighscores;}

    public long getID() {
        return ID;
    }
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getGameName() {
        return GameName;
    }
    public void setGamename(String gamename) {
        this.GameName = gamename;
    }
}
