using System.IO;
using UnityEngine;

public class ScoreSaver
{
    private string savePath;
    public ScoreSaver (string fileName)
    {
        savePath = Path.Combine(Application.persistentDataPath, fileName);
        Debug.Log(savePath);
    }

    public void SaveScore (int score)
    {
        using (
			var writer = new StreamWriter(File.Open(savePath, FileMode.Create))
		) {
            writer.WriteLine(score);
        }
    }

    public string ReadScore ()
    {
        using (
			var reader = new StreamReader(File.Open(savePath, FileMode.Open))
		) {
            return reader.ReadLine();
        }
    }
}