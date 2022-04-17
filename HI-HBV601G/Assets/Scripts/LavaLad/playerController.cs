using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;
public class playerController : MonoBehaviour
{

    public TextMeshProUGUI scoreText;
    public int score = 0;
    public float speed;

    bool ready;
    bool aiming = false;
    public static bool pauseEverything = false;

    Vector2 startPos;
    Vector2 endPos;

    private int exitRequests = 0;
    public Transform quitScreen;
    public Transform outcomeScreen;


    // Start is called before the first frame update
    void Start()
    {
        updateScore();
        pauseEverything = false;
    }

    // Update is called once per frame
    void Update()
    {
        
        if (this.GetComponent<Rigidbody2D>().velocity.magnitude <= 0.1f) {
            ready = true;
        } else {
            ready = false;
            //Debug.Log(this.GetComponent<Rigidbody2D>().velocity.magnitude.ToString());
        }

        if (Input.GetMouseButtonDown(0) == true && !aiming && ready) {
            aiming = true;
        }

        if (aiming && !pauseEverything) {
            startPos = this.transform.position;
            Vector3 shootPos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            shootPos.z = 0;
            shootPos = this.transform.position + (this.transform.position - shootPos);
            endPos = shootPos;
        }

        if (Input.GetMouseButtonUp(0) == true && aiming && ready && !pauseEverything) {
            endPos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            Shoot();
        }

        void Shoot() {
            aiming = false;
            //Debug.Log("Shooting @ " + endPos.ToString());
            //if(Vector2.Distance(startPos, endPos) > maxDist) {}
            Vector2 direction = startPos - endPos;
            this.GetComponent<Rigidbody2D>().AddForce(direction * speed);
        }

        //To Exit game
        if (Input.GetKeyDown(KeyCode.Escape)) {
            exitRequests++;
            StartCoroutine(ClickTime());

            if (exitRequests > 1)
            {
                quitScreen.GetChild(0).gameObject.SetActive(true);
                quitScreen.GetChild(1).gameObject.SetActive(true);
                pauseEverything = true;
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

    void updateScore() {
        scoreText.text = "Score: " + score;
    }

    private void OnCollisionEnter2D(Collision2D obj) {
        if (obj.transform.tag == "platform") {
            score++;
            Destroy(obj.gameObject);
            updateScore();
        }

        if (obj.transform.tag == "lava") {
            obj.transform.GetComponent<lavaController>().enabled = false;
            //SceneManager.LoadScene("CoolCharactervsLava");
            outcomeScreen.GetChild(0).gameObject.SetActive(true);
            outcomeScreen.GetChild(1).gameObject.SetActive(true);
            pauseEverything = true;
        }
    }

    public void LetHimMoveAgain() {
        pauseEverything = false;
    }
}
