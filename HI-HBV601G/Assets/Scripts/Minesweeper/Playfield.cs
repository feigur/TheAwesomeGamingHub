using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Playfield
{
    public static int w = 9;
    public static int h = 10;
    public static Element[,] elements = new Element[w,h];

    //Uncover all mines
    public static void uncoverMines()
    {
        foreach (Element elem in elements)
            if(elem.mine)
                elem.loadTexture(0);
    }

    //Is there a mine at location?
    public static bool mineAt(int x, int y)
    {
        if (x >= 0 && y >= 0 && x < w && y < h)
            return elements[x,y].mine;
        return false;
    }

    //Count adjacent mines
    public static int adjacentMines(int x, int y)
    {
        int count = 0;

        if (mineAt(x , y+1   )) //top
            count++;
        if (mineAt(x+1 , y+1 )) //top-right
            count++;
        if (mineAt(x+1 , y   )) //right
            count++;
        if (mineAt(x+1 , y-1 )) //bottom-right
            count++;
        if (mineAt(x , y-1   )) //bottom
            count++;
        if (mineAt(x-1 , y-1 )) //bottom-left
            count++;
        if (mineAt(x-1 , y   )) //left
            count++;
        if (mineAt(x-1 , y+1  )) //top-left
            count++;

        return count;
    }

    public static void FFuncover(int x, int y, bool[,] visited) {
        if (x >= 0 && y >= 0 &&  x < w && y < h)
        {
            if (visited[x,y])
                return;

            elements[x,y].loadTexture(adjacentMines(x,y));

            if (adjacentMines(x,y) > 0)
                return;

            visited[x,y] = true;

            FFuncover(x, y+1, visited);
            FFuncover(x+1, y+1, visited);
            FFuncover(x+1, y, visited);
            FFuncover(x+1, y-1, visited);
            FFuncover(x, y-1, visited);
            FFuncover(x-1, y-1, visited);
            FFuncover(x-1, y, visited);
            FFuncover(x-1, y+1, visited);
        }
    }

    public static bool isFinished()
    {
        foreach (Element elem in elements)
            if (elem.isCovered() && !elem.mine)
                return false;
        return true;
    }
}
