using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TouchInput : MonoBehaviour
{
    Transform lastTouchedButton = null;
    public Material lastTouchedButtonMaterial = null;

    [SerializeField]
    Game game;

    public Transform quitScreen;

    private int exitRequests = 0;
    private bool isPaused = false;

    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit))
            {
                Transform t = hit.transform;

                lastTouchedButton = t;
                lastTouchedButtonMaterial = t.GetComponent<MeshRenderer>().material;
                t.GetComponent<MeshRenderer>().material = game.pressedMaterial;
                if (!isPaused)
                {
                    if (t.name == "Del")
                        game.EraseCharacter();
                    else if (t.name == "Submit")
                    {
                        game.SubmitWord();
                    }
                    else
                        game.PlaceCharacter(t.name);
                }
                else
                {
                    if (t.name == "Yes")
                    {
                        UnityEditor.EditorApplication.isPlaying = false;
                        Application.Quit();
                    }
                    else // t.name == "No"
                    {
                        isPaused = false;
                        quitScreen.GetChild(0).gameObject.SetActive(false);
                        quitScreen.GetChild(1).gameObject.SetActive(false);
                    }
                }
            }
        }

        if (!isPaused)
        {
            if (Input.GetKeyDown(KeyCode.Escape))
            {
                exitRequests++;
                StartCoroutine(ClickTime());

                if (exitRequests > 1)
                {
                    isPaused = true;
                    quitScreen.GetChild(0).gameObject.SetActive(true);
                    quitScreen.GetChild(1).gameObject.SetActive(true);
                    // UnityEditor.EditorApplication.isPlaying = false;
                    //Application.Quit();
                }
            }
        }

        foreach(Touch touch in Input.touches)
        {
            if (touch.phase == TouchPhase.Began)
            {
                Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
                RaycastHit hit;
                if (Physics.Raycast(ray, out hit))
                {
                    Transform t = hit.transform;

                    lastTouchedButton = t;
                    lastTouchedButtonMaterial = t.GetComponent<MeshRenderer>().material;
                    t.GetComponent<MeshRenderer>().material = game.pressedMaterial;

                    if (!isPaused)
                    {
                        if (t.name == "Del")
                            game.EraseCharacter();
                        else if (t.name == "Submit")
                        {
                            game.SubmitWord();
                        }
                        else
                            game.PlaceCharacter(t.name);
                    }
                    else
                    {
                        print("here");
                        if (t.name == "Yes")
                        {
                            // UnityEditor.EditorApplication.isPlaying = false;
                            Application.Quit();
                        }
                        else // t.name == "No"
                        {
                            isPaused = false;
                            quitScreen.GetChild(0).gameObject.SetActive(false);
                            quitScreen.GetChild(1).gameObject.SetActive(false);
                        }
                    }
                }
            }

            if (touch.phase == TouchPhase.Ended)
            {
                // lastTouchedButtonMaterial = lastTouchedButton.GetComponent<MeshRenderer>().material;
                lastTouchedButton.GetComponent<MeshRenderer>().material = lastTouchedButtonMaterial;
            }
        }

        if (Input.GetMouseButtonUp(0))
        {
            print("check");
            lastTouchedButton.GetComponent<MeshRenderer>().material = lastTouchedButtonMaterial;
        }
    }

    IEnumerator ClickTime ()
    {
        yield return new WaitForSeconds(2.0f);
        exitRequests = 0;
    }
}