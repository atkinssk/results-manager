package uk.org.windswept.resultsmanager.ftp.vfs;

import com.google.common.base.Function;
import org.apache.ftpserver.ftplet.FtpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

/**
 * Created by 802998369 on 26/09/2016.
 */
public class VirtualFtpFile implements FtpFile
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFtpFile.class);
    private final VfsFile vfsFile;

    public VirtualFtpFile(VfsFile vfsFile)
    {
        this.vfsFile = vfsFile;
    }

    @Override
    public String getAbsolutePath()
    {
        LOGGER.info("getAbsolutePath");
        return vfsFile.getAbsolutePath();
    }

    @Override
    public String getName()
    {
        LOGGER.info("getName");
        return vfsFile.getName();
    }

    @Override
    public boolean isHidden()
    {
        LOGGER.info("isHidden");
        return false;
    }

    @Override
    public boolean isDirectory()
    {
        LOGGER.info("isDirectory");
        return vfsFile.getType().equals(VfsFile.Type.DIRECTORY);
    }

    @Override
    public boolean isFile()
    {
        LOGGER.info("isFile");
        return vfsFile.getType().equals(VfsFile.Type.FILE);
    }

    @Override
    public boolean doesExist()
    {
        LOGGER.info("doesExist");
        // TODO implement
        return vfsFile.isExists();
    }

    @Override
    public boolean isReadable()
    {
        LOGGER.info("isReadable");
        // TODO implement
        return true;
    }

    @Override
    public boolean isWritable()
    {
        LOGGER.info("isWritable");
        // TODO implement
        return true;
    }

    @Override
    public boolean isRemovable()
    {
        LOGGER.info("isRemovable");
        // TODO implement
        return true;
    }

    @Override
    public String getOwnerName()
    {
        LOGGER.info("getOwnerName");
        // TODO implement
        return "user";
    }

    @Override
    public String getGroupName()
    {
        LOGGER.info("getGroupName");
        // TODO implement
        return "group";
    }

    @Override
    public int getLinkCount()
    {
        LOGGER.info("getLinkCount");
        return 0;
    }

    @Override
    public long getLastModified()
    {
        LOGGER.info("getLastModified");
        return vfsFile.getLastModified();
    }

    @Override
    public boolean setLastModified(long l)
    {
        LOGGER.info("setLastModified");
        vfsFile.setLastModified(l);
        return false;
    }

    @Override
    public long getSize()
    {
        LOGGER.info("getSize");
        return vfsFile.getSize();
    }

    @Override
    public boolean mkdir()
    {
        LOGGER.info("mkdir");
        // TODO implement
        return false;
    }

    @Override
    public boolean delete()
    {
        LOGGER.info("delete");
        // TODO implement
        return false;
    }

    @Override
    public boolean move(FtpFile ftpFile)
    {
        LOGGER.info("");
        // TODO implement
        return false;
    }

    @Override
    public List<FtpFile> listFiles()
    {
        LOGGER.info("listFiles");
        return transform (newArrayList(vfsFile.getChildren()), new Function<VfsFile, VirtualFtpFile>(){
            @Override
            public VirtualFtpFile apply(VfsFile input)
            {
                return new VirtualFtpFile(input);
            }
        });
    }

    @Override
    public OutputStream createOutputStream(long offset) throws IOException
    {
        LOGGER.info("createOutputStream offset:{}", offset);
        // TODO implement
        return new StubOutputStream(vfsFile);
    }

    class StubOutputStream extends OutputStream
    {
        private final VfsFile vfsFile;
        StringBuffer buffer = new StringBuffer();

        public StubOutputStream(VfsFile vfsFile)
        {
            this.vfsFile = vfsFile;
        }

        @Override
        public void write(int b) throws IOException
        {
            buffer.append((char)b);
        }

        @Override
        public void close() throws IOException
        {
            LOGGER.info("Buffer contains {}", buffer.length());
            // Attache us under a directory now that we have some content
            vfsFile.addTo(vfsFile.getParent());
            vfsFile.setContent(buffer.toString());
        }
    }

    @Override
    public InputStream createInputStream(long offset) throws IOException
    {
        LOGGER.info("createInputStream offset:{}", offset);
        // TODO implement
        return null;
    }
}
