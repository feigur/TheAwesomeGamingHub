using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class Element : MonoBehaviour
{
    //GAME
    public bool mine;
    public bool gameFinished = false;
    public Sprite[] emptyTextures;
    public Sprite mineTexture;
    public Sprite flagTexture;

    


    // Start is called before the first frame update
    void Start()
    {
        mine = Random.value < 0.15;

        int x = (int)transform.position.x;
        int y = (int)transform.position.y;
        Playfield.elements[x,y] = this;
    }

    public void loadTexture(int adjacentCount)
    {
        if (mine)
            GetComponent<SpriteRenderer>().sprite = mineTexture;
        else
            GetComponent<SpriteRenderer>().sprite = emptyTextures[adjacentCount];
    }

    public bool isCovered()
    {
        return GetComponent<SpriteRenderer>().sprite.texture.name == "covered_block";
    }

    void OnMouseUpAsButton()
    {
        TimerHandler gameb = GetComponentInParent<TimerHandler>() as TimerHandler;
        if(mine)
        {
            Playfield.uncoverMines();
            //SceneManager.LoadScene("Minesweeper");
            gameb.stopTheClock();
        } else
        {
            gameb.startTheClock();
            int x = (int)transform.position.x;
            int y = (int)transform.position.y;
            loadTexture(Playfield.adjacentMines(x,y));

            Playfield.FFuncover(x,y, new bool[Playfield.w, Playfield.h]);

            if (Playfield.isFinished())
            {
                print("you win");
            }
        }
    }
}
