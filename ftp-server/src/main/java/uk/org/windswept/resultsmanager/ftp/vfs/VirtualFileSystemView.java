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

    private final VfsFile homeDirectory = VfsFile.newRootInstance();

    private VfsFile workingDirectory = homeDirectory;

    public VirtualFileSystemView(User user)
    {
        this.user = user;
    }

    private FtpFile asVirtualFtpFile(VfsFile vfsFile)
    {
        FtpFile result = null;
        if (vfsFile != null)
        {
            result = new VirtualFtpFile(vfsFile);
        }
        return result;
    }

    @Override
    public FtpFile getHomeDirectory() throws FtpException
    {
        LOGGER.info("getHomeDirectory");
        return asVirtualFtpFile(homeDirectory);
    }

    @Override
    public FtpFile getWorkingDirectory() throws FtpException
    {
        LOGGER.info("getWorkingDirectory");
        return asVirtualFtpFile(workingDirectory);
    }

    @Override
    public boolean changeWorkingDirectory(String dir) throws FtpException
    {
        LOGGER.info("changeWorkingDirectory dir:{}", dir);
        VfsFile newDir = workingDirectory.getSubDirectory(dir);
        if (newDir != null)
        {
            workingDirectory = newDir;
        }
        return newDir != null;
    }

    @Override
    public FtpFile getFile(String name) throws FtpException
    {
        LOGGER.info("getFile name:{}", name);

        VfsFile file;
        if (name.equals("./"))
        {
            LOGGER.info("working directory");
            file = workingDirectory;
        }
        else
        {
            file = workingDirectory.getChildFile(name);
            if (file == null)
            {
                LOGGER.info("Missing file {}", name);
                // Create a link to the parent so that we can later add ourself as a child at the end of an upload
                file = VfsFile.newMissingInstance(name, workingDirectory);
            }
            else
            {
                LOGGER.info("Exists {}", name);
            }
        }
        return asVirtualFtpFile(file);
    }

    @Override
    public boolean isRandomAccessible() throws FtpException
    {
        // Always say this
        return false;
    }

    @Override
    public void dispose()
    {
        LOGGER.info("dispose");
    }
}
