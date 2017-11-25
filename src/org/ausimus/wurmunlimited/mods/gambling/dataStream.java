package org.ausimus.wurmunlimited.mods.gambling;
import com.wurmonline.server.items.Item;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class dataStream
{
    private static Properties prop = new Properties();

    /**
     * {@inheritDoc}
     *
     * @param target
     * @param value
     */
    public static void writeData(Item target, long value)
    {
        try
        {
            FileInputStream in = new FileInputStream("mods/GambleMod/db/dataStream.properties");
            prop.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("mods/GambleMod/db/dataStream.properties");
            prop.setProperty(String.valueOf(target.getWurmId()), String.valueOf(value));
            prop.store(out, null);
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param target
     */
    public static void getData(Item target)
    {
        try
        {
            InputStream input = new FileInputStream("mods/GambleMod/db/dataStream.properties");
            prop.load(input);
            prop.getProperty(String.valueOf(target.getWurmId()));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}