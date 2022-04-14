using UnityEngine;
using System.IO;

public class GameDataReader
{
    BinaryReader reader;

    public GameDataReader (BinaryReader reader)
    {
        this.reader = reader;
    }

    public float readFloat ()
    {
        return reader.ReadSingle();
    }

    public int readInt ()
    {
        return reader.ReadInt32();
    }
}
