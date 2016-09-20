package uk.org.windswept.resultsmanager.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class Server
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final FtpServerFactory serverFactory;
    private final FtpServer server;

    public Server(FtpServerFactory serverFactory)
    {
        this.serverFactory = serverFactory;
        server = serverFactory.createServer();
    }

    public Server()
    {
        this(new FtpServerFactory());
    }

    public Server(UserManager userManager)
    {
        serverFactory = new FtpServerFactory();
        serverFactory.setUserManager(userManager);
        server = serverFactory.createServer();
    }

    public void start() throws FtpException
    {
        LOGGER.info("Starting FTP server");
        try
        {
            server.start();
        }
        catch (FtpException e)
        {
            LOGGER.error ("Unable to start FTP Server", e);
            throw e;
        }
    }

    public void stop()
    {
        if (server != null)
        {
            LOGGER.info("Stopping FTP server");
            server.stop();
        }
    }
}
