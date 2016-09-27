package uk.org.windswept.test.file;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class FileMatcher extends BaseFileMatcher
{
    public FileMatcher ()
    {
        super("Should be a file", "is not a file");
    }

    @Override
    public boolean matches (File file)
    {
        return file.isFile();
    }
}
