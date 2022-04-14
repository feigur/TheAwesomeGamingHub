using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class camerTracker : MonoBehaviour
{

    public GameObject target;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        this.transform.position = Vector2.Lerp(this.transform.position,target.transform.position,0.5f);
    }
}
