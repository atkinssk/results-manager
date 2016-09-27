package uk.org.windswept.test.file;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class ExistsMatcher extends BaseFileMatcher
{
    public ExistsMatcher ()
    {
        super("Should exist", "does not exist");
    }

    @Override
    public boolean matches (File file)
    {
        return file.exists();
    }
}
