package uk.org.windswept.resultsmanager.ftp.vfs;

import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 802998369 on 26/09/2016.
 */
public class VirtualFileSystemFactory implements FileSystemFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFileSystemFactory.class);

    @Override
    public FileSystemView createFileSystemView(User user) throws FtpException
    {
        LOGGER.info("createFileSystemView user:{}", user);
        return new VirtualFileSystemView(user);
    }
}
