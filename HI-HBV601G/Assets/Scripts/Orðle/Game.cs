using UnityEngine;
using System;
using TMPro;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Diagnostics;

public class Game : MonoBehaviour
{
    public static event Func<int, IEnumerator> submissionEvent;

    private Dictionary<string, Color> keyColours;

    private ScoreSaver scoreSaver;

    private Stopwatch stopwatch = new Stopwatch();

    public Color correctColour;
    public Color presentColour;
    public Color notPresentColour;
    public Transform ScoreTextTransform;

    public Material pressedMaterial;

    private string savePath;
    private string word = "HOPUR";
    private int submissions = 0;
    private int offset = 0;
    private string guess = "";
    private int score;

    [SerializeField]
    public List<Transform> slot;

    [SerializeField]
    public List<Transform> keys;

    private void Awake ()
    {
        scoreSaver = new ScoreSaver("orðle.txt");
        scoreSaver.SaveScore(9999);
        keyColours = new Dictionary<string, Color>();
        keyColours.Add("Q", Color.black);
        keyColours.Add("W", Color.black);
        keyColours.Add("E", Color.black);
        keyColours.Add("R", Color.black);
        keyColours.Add("T", Color.black);
        keyColours.Add("Y", Color.black);
        keyColours.Add("U", Color.black);
        keyColours.Add("I", Color.black);
        keyColours.Add("O", Color.black);
        keyColours.Add("P", Color.black);
        keyColours.Add("A", Color.black);
        keyColours.Add("S", Color.black);
        keyColours.Add("D", Color.black);
        keyColours.Add("F", Color.black);
        keyColours.Add("G", Color.black);
        keyColours.Add("H", Color.black);
        keyColours.Add("J", Color.black);
        keyColours.Add("K", Color.black);
        keyColours.Add("L", Color.black);
        keyColours.Add("Z", Color.black);
        keyColours.Add("X", Color.black);
        keyColours.Add("C", Color.black);
        keyColours.Add("V", Color.black);
        keyColours.Add("B", Color.black);
        keyColours.Add("N", Color.black);
        keyColours.Add("M", Color.black);
        print(keyColours["H"]);
		//savePath = Path.Combine(Application.persistentDataPath, "orðle.txt");
	}

    private void OnEnable ()
    {
        SlotRotator[] rotators = FindObjectsOfType<SlotRotator>();
        foreach (SlotRotator r in rotators)
            submissionEvent += r.onSubmission;

        ScoreTextTransform.GetComponent<TextMeshPro>().text = scoreSaver.ReadScore();
    }

    private void OnDisable ()
    {
        SlotRotator[] rotators = FindObjectsOfType<SlotRotator>();
        foreach (SlotRotator r in rotators)
            submissionEvent -= r.onSubmission;
    }

    public void PlaceCharacter (string c)
    {
        if (offset < 5)
        {
            Transform t = slot[5 * submissions + offset];
            TextMeshPro[] ts = t.GetComponentsInChildren<TextMeshPro>();
            foreach (TextMeshPro tmp in ts)
                tmp.text = c;

            guess = guess.Insert(guess.Length, c);
            offset++;
        }
    }

    public void EraseCharacter ()
    {
        if (offset > 0)
        {
            offset--;
            Transform t = slot[5 * submissions + offset];
            TextMeshPro[] ts = t.GetComponentsInChildren<TextMeshPro>();
            foreach (TextMeshPro tmp in ts)
                tmp.text = "";

            guess = guess.Remove(guess.Length - 1);
        }
    }

    public void StartGame ()
    {
        stopwatch.Start();
    }

    public void SubmitWord ()
    {
        if (offset < 5)
            return;

        int slotNum = 0;
        int matches = 0;
        foreach (char c in guess)
        {
            Transform t = slot[5 * submissions + slotNum];
            Material m = t.GetChild(1).GetComponent<MeshRenderer>().material;

            if (!word.Contains(c))
            {
                m.SetColor("_Color", notPresentColour);
            }
            else
            {
                m.SetColor("_Color", presentColour);

                if (c == word[slotNum])
                {
                    m.SetColor("_Color", correctColour);
                    matches++;
                }
            }

            Transform tr = GameObject.Find(c.ToString()).transform;
            print(tr.name);
            Material mat = tr.GetComponent<MeshRenderer>().material;

            Color keyColor = keyColours[tr.name];

            if (keyColor != correctColour)
            {
                if (!word.Contains(c))
                {
                    keyColours[tr.name] = notPresentColour;
                }
                else
                {
                    keyColours[tr.name] = presentColour;

                    if (c == word[slotNum])
                    {
                        keyColours[tr.name] = correctColour;
                    }
                }
            }

            mat.SetColor("_Color", keyColours[tr.name]);

            slotNum++;
        }

    	StartCoroutine(SaveSore(matches));

        // CalculateScore();

        offset = 0;
        guess = "";
        matches = 0;
        submissions++;
    }

    private IEnumerator RotateSlots ()
    {
        foreach (Func<int, IEnumerator> handler in submissionEvent.GetInvocationList())
            StartCoroutine(handler.Invoke(submissions));

        yield return new WaitForSeconds(3f);
    }

    private IEnumerator SaveSore (int matches)
    {
        yield return StartCoroutine(RotateSlots());

        CalculateScore(0.3f);

        if (matches == 5 || submissions == 6)
        {
            scoreSaver.SaveScore(score);
            ScoreTextTransform.GetComponent<TextMeshPro>().text = scoreSaver.ReadScore();
        }
    }

    private void CalculateScore (float timePassed)
    {

    }
}