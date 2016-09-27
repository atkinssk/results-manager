package uk.org.windswept.test.file;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class DirectoryMatcher extends BaseFileMatcher
{
    public DirectoryMatcher ()
    {
        super("Should be a directory", "is not a directory");
    }

    @Override
    public boolean matches (File file)
    {
        return file.isDirectory();
    }
}
