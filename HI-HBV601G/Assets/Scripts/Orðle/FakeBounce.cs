using UnityEngine;
using System.IO;

public class FakeBounce : MonoBehaviour
{
    // private string savePath;
    // GameDataWriter writer;

    private void Awake ()
    {
        //writer = new GameDataWriter(new BinaryWriter());
        //savePath = Path.Combine(Application.persistentDataPath, "saveFile");
    }

    private void Update()
    {
        transform.position = new Vector3(0, 0.7f * Mathf.Sin(2f * Time.time) + 1.2f, 0);
    }
}
