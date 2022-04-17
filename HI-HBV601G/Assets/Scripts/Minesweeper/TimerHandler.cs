using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using TMPro;


public class TimerHandler : MonoBehaviour
{
    float startingTime;
    float theTime = 0;
    public static bool startTimer;

    private int exitRequests = 0;
    public Transform quitScreen;
    public TextMeshProUGUI timerText;

    public TextMeshProUGUI outcomeText;
    
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

        //To Exit game
        if (Input.GetKeyDown(KeyCode.Escape)) {
            exitRequests++;
            StartCoroutine(ClickTime());

            if (exitRequests > 1)
            {
                quitScreen.GetChild(0).gameObject.SetActive(true);
                quitScreen.GetChild(1).gameObject.SetActive(true);
                // UnityEditor.EditorApplication.isPlaying = false;
                //Application.Quit();
            }
        }
    }

    IEnumerator ClickTime ()
    {
        yield return new WaitForSeconds(2.0f);
        exitRequests = 0;
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
        timerText.text = "Time: " + Mathf.Round(theTime) + " sec";
    }

    public void WhatIsTheOutcome(string x) {
        outcomeText.text = "YOU " + x;
    }
}