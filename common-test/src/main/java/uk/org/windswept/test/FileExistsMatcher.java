package uk.org.windswept.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class FileExistsMatcher extends BaseMatcher<File>
{
    public boolean matches (Object item)
    {
        File file = (File)item;
        return file.exists() && file.isFile();

    }

    public void describeTo (Description description)
    {
        description.appendText("Should exist and be a file");
    }

    @Override
    public void describeMismatch (Object item, Description description)
    {
        File file = (File)item;
        if (!file.exists())
        {
            description.appendValue(file).appendText(" did not exist");
        }
        else if(!file.isFile())
        {
            description.appendValue(file).appendText(" exists but is not a file");
        }
    }

    public static FileExistsMatcher fileExists()
    {
        return new FileExistsMatcher();
    }
}
