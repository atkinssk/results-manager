package uk.org.windswept.resultsmanager.ftp;

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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class ServerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);


    @Test
    public void shouldStartServer() throws Exception
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

        Server server = new Server();
        server.start();



        FTPClient client = new FTPClient();
        client.connect(InetAddress.getLocalHost());
        client.login("test", "test");
        FTPFile[] files = client.listFiles();
        LOGGER.info("files:{}", newArrayList(files));
        FTPFile[] directories = client.listDirectories();
        LOGGER.info("directories:{}", newArrayList(directories));

        server.stop();
    }
}