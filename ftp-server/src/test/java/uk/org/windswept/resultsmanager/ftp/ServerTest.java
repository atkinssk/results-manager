package uk.org.windswept.resultsmanager.ftp;

import com.google.common.base.Predicate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UserManagerFactory;
import org.apache.ftpserver.usermanager.impl.AbstractUserManager;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class ServerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

    public static final int PORT = 2222;

    private Server server;


    private UserManager createUserManager () throws IOException, FtpException
    {
        final File userPropertiesFile = new File("target/users.properties");
        Properties userProperties = new Properties();
        userProperties.store(new FileWriter(userPropertiesFile),"User Properties");

        PropertiesUserManagerFactory factory = new PropertiesUserManagerFactory();
        factory.setFile(userPropertiesFile);
        UserManager userManager = factory.createUserManager();

        BaseUser user = new BaseUser();
        user.setName("test");
        user.setPassword("test");
        user.setHomeDirectory("target");

        userManager.save(user);
        return userManager;
    }

    @Before
    public void startServer () throws IOException, FtpException
    {
        UserManager userManager = createUserManager();

        LoggingFtplet loggingFtplet = new LoggingFtplet();
        server = new Server().withUserManager(userManager).withPort(PORT).withFtplet(loggingFtplet);
        server.start();
    }

    @After
    public void stopServer ()
    {
        server.stop();
    }

    @Test
    public void shouldStartServer() throws Exception
    {
        FTPClient client = new FTPClient();
        client.connect(InetAddress.getLocalHost(), PORT);
        client.login("test", "test");

        List<FTPFile> ftpFiles = newArrayList(client.listFiles());
        for (FTPFile file : ftpFiles )
        {
            LOGGER.info("{}", file);
        }

        Collection<FTPFile> files = filter(ftpFiles, new Predicate<FTPFile>(){
            public boolean apply (FTPFile input)
            {
                return input.isFile();
            }
        });

        assertThat(files.iterator().next().getName(), is("users.properties"));
    }
}