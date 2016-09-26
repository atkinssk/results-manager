package uk.org.windswept.resultsmanager.ftp.vfs;

import org.apache.ftpserver.ftplet.FtpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by 802998369 on 26/09/2016.
 */
public class VirtualFtpFile implements FtpFile
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFtpFile.class);

    @Override
    public String getAbsolutePath()
    {
        LOGGER.info("getAbsolutePath");
        return null;
    }

    @Override
    public String getName()
    {
        LOGGER.info("getName");
        return null;
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
        return false;
    }

    @Override
    public boolean isFile()
    {
        LOGGER.info("isFile");
        return false;
    }

    @Override
    public boolean doesExist()
    {
        LOGGER.info("doesExist");
        return false;
    }

    @Override
    public boolean isReadable()
    {
        LOGGER.info("isReadable");
        return false;
    }

    @Override
    public boolean isWritable()
    {
        LOGGER.info("isWritable");
        return false;
    }

    @Override
    public boolean isRemovable()
    {
        LOGGER.info("isRemovable");
        return false;
    }

    @Override
    public String getOwnerName()
    {
        LOGGER.info("getOwnerName");
        return null;
    }

    @Override
    public String getGroupName()
    {
        LOGGER.info("getGroupName");
        return null;
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
        return 0;
    }

    @Override
    public boolean setLastModified(long l)
    {
        LOGGER.info("setLastModified");
        return false;
    }

    @Override
    public long getSize()
    {
        LOGGER.info("getSize");
        return 0;
    }

    @Override
    public boolean mkdir()
    {
        LOGGER.info("mkdir");
        return false;
    }

    @Override
    public boolean delete()
    {
        LOGGER.info("delete");
        return false;
    }

    @Override
    public boolean move(FtpFile ftpFile)
    {
        LOGGER.info("");
        return false;
    }

    @Override
    public List<FtpFile> listFiles()
    {
        LOGGER.info("listFiles");
        return null;
    }

    @Override
    public OutputStream createOutputStream(long l) throws IOException
    {
        LOGGER.info("createOutputStream");
        return null;
    }

    @Override
    public InputStream createInputStream(long l) throws IOException
    {
        LOGGER.info("createInputStream");
        return null;
    }
}
