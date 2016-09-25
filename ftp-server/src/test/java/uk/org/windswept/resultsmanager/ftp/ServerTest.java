package uk.org.windswept.resultsmanager.ftp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
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
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Collections2.transform;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static uk.org.windswept.test.FileExistsMatcher.fileExists;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class ServerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

    public static final int PORT = 2222;

    private Server server;

    private List<Authority> createAuthorities(Authority... authorities)
    {
        return newArrayList(authorities);
    }

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
        user.setAuthorities(createAuthorities(new WritePermission()));

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

    private FTPClient getFtpClient() throws IOException
    {
        FTPClient client = new FTPClient();
        client.connect(InetAddress.getLocalHost(), PORT);
        client.login("test", "test");
        return client;
    }

    private List<String> listFileName(FTPClient client) throws IOException
    {
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

        Collection<String> fileNames = transform(files, new Function<FTPFile, String>(){
            public String apply(FTPFile input)
            {
                return input.getName();
            }
        });

        return newArrayList(fileNames);
    }

    @Test
    public void shouldStartServer() throws Exception
    {
        FTPClient client = getFtpClient();

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

    @Test
    public void shouldUploadFile() throws Exception
    {
        FTPClient client = getFtpClient();
        String pwd = client.printWorkingDirectory();
        LOGGER.info("pwd:{}", pwd);


        String remote = "results.html";
        URL resultsFile = getClass().getClassLoader().getResource("data/results.html");
        LOGGER.info("Loading results from {}", resultsFile);
        assertThat(client.storeFile(remote, resultsFile.openStream()), is(true));

        // Should exist in target directory now
        File outputFile = new File("target", "results.html");
        assertThat(outputFile, fileExists());

        // Should be returned by the ftp server list command
        List<String> filenames = listFileName(client);
        assertThat(filenames, hasItem("results.html"));
    }

}