package uk.org.windswept.resultsmanager.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class Server
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final FtpServerFactory serverFactory;
    private FtpServer server;

    public Server(FtpServerFactory serverFactory)
    {
        this.serverFactory = serverFactory;
    }

    public Server()
    {
        this(new FtpServerFactory());
    }

    public Server withUserManager(UserManager userManager)
    {
        serverFactory.setUserManager(userManager);
        return this;
    }

    public Server withPort(Integer port)
    {
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);
        serverFactory.addListener("default", listenerFactory.createListener());
        return this;
    }

    public void start() throws FtpException
    {
        LOGGER.info("Starting FTP server");
        try
        {
            server = serverFactory.createServer();
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
