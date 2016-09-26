package uk.org.windswept.resultsmanager.ftp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import static org.hamcrest.Matchers.empty;
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

    public static final File USER_HOME_DIRECTORY = FileUtils.getFile("target", "userWorkingDirectory");

    private Server server;
    private LoggingFtplet loggingFtplet;

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
        user.setHomeDirectory(USER_HOME_DIRECTORY.getPath());
        user.setAuthorities(createAuthorities(new WritePermission()));

        userManager.save(user);
        return userManager;
    }

    @Before
    public void startServer () throws IOException, FtpException
    {
        UserManager userManager = createUserManager();

        loggingFtplet = new LoggingFtplet();
        server = new Server().
                withUserManager(userManager).
                withPort(PORT).
                withFtplet(loggingFtplet).
                withCallbackFtplet();
        server.start();
    }

    @Before
    public void clearWorkingDirectory() throws IOException
    {
        if(!USER_HOME_DIRECTORY.exists())
        {
            LOGGER.info("Create User Home Directory {}", USER_HOME_DIRECTORY);
            assertThat("Unable to create User Home Directory", USER_HOME_DIRECTORY.mkdirs(), is(true));
        }
        else
        {
            LOGGER.info("Clear User Home Directory {}", USER_HOME_DIRECTORY);
            FileUtils.cleanDirectory(USER_HOME_DIRECTORY);
        }
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
        return listFileName(client, null);
    }

    private List<String> listFileName(FTPClient client, String path) throws IOException
    {
        List<FTPFile> ftpFiles = newArrayList(client.listFiles(path));
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

        assertThat(files, empty());
    }

    @Test
    public void shouldUploadFileToUserHomeDirectory() throws Exception
    {
        FTPClient client = getFtpClient();
        String pwd = client.printWorkingDirectory();
        LOGGER.info("pwd:{}", pwd);


        String remote = "results.html";
        URL resultsFile = getClass().getClassLoader().getResource("data/results.html");
        LOGGER.info("Loading results from {}", resultsFile);
        assertThat(client.storeFile(remote, resultsFile.openStream()), is(true));

        // Should exist in target directory now
        File outputFile = new File(USER_HOME_DIRECTORY, remote);
        assertThat(outputFile, fileExists());

        // Should be returned by the ftp server list command
        List<String> filenames = listFileName(client);
        assertThat(filenames, hasItem("results.html"));
    }

    @Test
    public void shouldUploadFileToSubDirectory() throws Exception
    {
        FTPClient client = getFtpClient();
        String pwd = client.printWorkingDirectory();
        LOGGER.info("pwd:{}", pwd);

        String remoteDir = "2016/DinghyRegatta";
        String remote = remoteDir + "/results.html";
        URL resultsFile = getClass().getClassLoader().getResource("data/results.html");
        LOGGER.info("Loading results from {}", resultsFile);
        assertThat(client.storeFile(remote, resultsFile.openStream()), is(true));

        // Should exist in target directory now
        File outputFile = new File(USER_HOME_DIRECTORY, remote);
        assertThat(outputFile, fileExists());

        // Should be returned by the ftp server list command
        List<String> filenames = listFileName(client, remoteDir);
        assertThat(filenames, hasItem("results.html"));
    }

    @Test
    @Ignore("This is only for investigation")
    public void shouldWaitForDisconnect() throws InterruptedException
    {
        LOGGER.info("Waiting for disconnect callback");
        loggingFtplet.waitForDisconnect();
    }

}