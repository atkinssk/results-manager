package uk.org.windswept.resultsmanager.ftp.vfs;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 802998369 on 27/09/2016.
 */
public class VfsFileTest
{
    VfsFile root = VfsFile.newRootInstance();
    VfsFile subDir = VfsFile.newDirectoryInstance("dir1").addTo(root);
    VfsFile file1 = VfsFile.newFileInstance("file1").addTo(root);
    VfsFile file2 = VfsFile.newFileInstance("file2").addTo(subDir);

    @Test
    public void shouldHandleRoot()
    {
        assertThat(root.getAbsolutePath(), is("/"));
    }

    @Test
    public void shouldHandleSingleLevelDirectory()
    {
        assertThat(subDir.getAbsolutePath(), is("/dir1/"));
    }

    @Test
    public void shouldHandleTopLevelFile() throws Exception
    {
        assertThat(file1.getAbsolutePath(), is("/file1"));
    }

    @Test
    public void shouldHandleLeafFile() throws Exception
    {
        assertThat(file2.getAbsolutePath(), is("/dir1/file2"));
    }
}