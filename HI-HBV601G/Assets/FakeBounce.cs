using UnityEngine;

public class FakeBounce : MonoBehaviour
{
    void Update()
    {
        transform.position = new Vector3(0, 0.7f * Mathf.Sin(2f * Time.time) + 1.2f, 0);
    }
}
