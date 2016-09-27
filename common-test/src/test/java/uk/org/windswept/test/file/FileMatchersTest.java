package uk.org.windswept.test.file;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static uk.org.windswept.test.file.FileMatchers.exists;
import static uk.org.windswept.test.file.FileMatchers.isDirectory;
import static uk.org.windswept.test.file.FileMatchers.isFile;

/**
 * Created by steveatkinson on 27/09/2016.
 */
public class FileMatchersTest
{

    private File directory = new File("target");
    private File file = new File("pom.xml");
    private File missing = new File("missing");

    @Test
    public void shouldExistsDirectory () throws Exception
    {
        assertThat(directory, exists());
    }

    @Test
    public void shouldExistsFile () throws Exception
    {
        assertThat(file, exists());
    }

    @Test
    public void shouldNotExists () throws Exception
    {
        assertThat(missing, not(exists()));
    }

    @Test
    public void shouldIsFile () throws Exception
    {
        assertThat(file, isFile());
    }

    @Test
    public void shouldNotIsFile () throws Exception
    {
        assertThat(missing, not(isFile()));
    }

    @Test
    public void shouldIsDirectory () throws Exception
    {
        assertThat(directory, isDirectory());

    }

    @Test
    public void shouldNotIsDirectory () throws Exception
    {
        assertThat(missing, not(isDirectory()));

    }

}