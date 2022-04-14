using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class TimerHandler : MonoBehaviour
{
    float startingTime;
    float theTime = 0;
    public static bool startTimer;

    public TextMeshProUGUI timerText;
    //public Button m_RestartButton;
    
    void Start() {
        startTimer = false;
        updateTimer();
    }

    void Update() {
        if(startTimer){
            theTime = Time.time - startingTime;
            updateTimer();
            //print(Mathf.Round(theTime));
        }
    }

    public void startTheClock() {
        if(!startTimer){
            startingTime = Time.time;
            theTime = 0;
        }
        startTimer = true;
    }

    public void stopTheClock() {
        startTimer = false;
    }

    public void updateTimer() {
        timerText.text = "Time: " + Mathf.Round(theTime);
    }

}
