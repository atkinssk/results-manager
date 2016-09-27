package uk.org.windswept.test.file;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.File;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public abstract class BaseFileMatcher extends BaseMatcher<File>
{
    private final String describeToDescription;
    private final String mismatchDescription;

    public BaseFileMatcher (String describeToDescription, String mismatchDescription)
    {
        this.describeToDescription = describeToDescription;
        this.mismatchDescription = mismatchDescription;
    }

    public abstract boolean matches(File file);

    public boolean matches (Object item)
    {
        return matches((File)item);
    }

    public void describeTo (Description description)
    {
        description.appendText(describeToDescription);
    }

    @Override
    public void describeMismatch (Object item, Description description)
    {
        description.appendValue(item).appendText(" " + mismatchDescription);
    }
}
