using System.Collections;
using UnityEngine;

public class SlotRotator : MonoBehaviour
{
    public float delay = 0f;
    public int row = 0;

    public IEnumerator onSubmission (int currentRow)
    {
        if (currentRow != row)
            yield break;

        yield return new WaitForSecondsRealtime(delay);

        for (int i = 0; i < 180; i++)
        {
            transform.Rotate(1, 0, 0);
            yield return null;
        }
    }
}