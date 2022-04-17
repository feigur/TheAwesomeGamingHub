using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ButtonController : MonoBehaviour
{
    public Transform quitScreen;
    public Button m_RestartButton, m_UpdateButton, m_QuitYesButton, m_QuitNoButton;

    // Start is called before the first frame update
    void Start()
    {
        m_QuitYesButton.onClick.AddListener(LeaveGame);
        m_QuitNoButton.onClick.AddListener(DoNotLeaveGame);
        m_RestartButton.onClick.AddListener(RestartGame);
        m_UpdateButton.onClick.AddListener(UpdateYourScore);
    }

    void LeaveGame() {
        UnityEditor.EditorApplication.isPlaying = false;
        Application.Quit();
    }

    void DoNotLeaveGame() {
        quitScreen.GetChild(0).gameObject.SetActive(false);
        quitScreen.GetChild(1).gameObject.SetActive(false);
    }

    void RestartGame() {
        SceneManager.LoadScene("Minesweeper");
    }

    void UpdateYourScore() {
        //Do something
    }
}
