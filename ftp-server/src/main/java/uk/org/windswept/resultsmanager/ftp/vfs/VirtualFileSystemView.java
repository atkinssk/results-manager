package uk.org.windswept.resultsmanager.ftp.vfs;

import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 802998369 on 26/09/2016.
 */
public class VirtualFileSystemView implements FileSystemView
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFileSystemView.class);

    private final User user;

    public VirtualFileSystemView(User user)
    {
        this.user = user;
    }

    @Override
    public FtpFile getHomeDirectory() throws FtpException
    {
        LOGGER.info("getHomeDirectory");
        return null;
    }

    @Override
    public FtpFile getWorkingDirectory() throws FtpException
    {
        LOGGER.info("getWorkingDirectory");
        return null;
    }

    @Override
    public boolean changeWorkingDirectory(String dir) throws FtpException
    {
        LOGGER.info("changeWorkingDirectory dir:{}", dir);
        return false;
    }

    @Override
    public FtpFile getFile(String file) throws FtpException
    {
        LOGGER.info("getFile dir:{}", file);
        return null;
    }

    @Override
    public boolean isRandomAccessible() throws FtpException
    {
        return false;
    }

    @Override
    public void dispose()
    {

    }
}
