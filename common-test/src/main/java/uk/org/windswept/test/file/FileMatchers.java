package uk.org.windswept.test.file;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import uk.org.windswept.test.file.ExistsMatcher;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class FileMatchers
{
    public static ExistsMatcher exists ()
    {
        return new ExistsMatcher();
    }

    public static FileMatcher isFile ()
    {
        return new FileMatcher();
    }

    public static DirectoryMatcher isDirectory ()
    {
        return new DirectoryMatcher();
    }

    // TODO isReadable
    // TODO isWritable
}
