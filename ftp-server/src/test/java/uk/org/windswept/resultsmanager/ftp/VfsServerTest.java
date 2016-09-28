package uk.org.windswept.resultsmanager.ftp;

import org.apache.commons.io.FileUtils;
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
import uk.org.windswept.resultsmanager.ftp.vfs.VirtualFileSystemFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static uk.org.windswept.test.file.FileMatchers.exists;
import static uk.org.windswept.test.file.FileMatchers.isFile;

/**
 * Created by 802998369 on 20/09/2016.
 */
public class VfsServerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VfsServerTest.class);

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
                withCallbackFtplet().
                withFileSystemFactory(new VirtualFileSystemFactory());
        server.start();
    }

    @After
    public void stopServer ()
    {
        server.stop();
    }

    private FtpClientHelper getFtpClientHelper() throws IOException
    {
        return new FtpClientHelper(InetAddress.getLocalHost(), PORT, "test", "test");
    }

    @Test
    public void shouldStartServer() throws Exception
    {
        FtpClientHelper client = getFtpClientHelper();
        List<String> files = client.listFileNames();
        assertThat(files, empty());
    }

    @Test
    public void shouldUploadFileToUserHomeDirectory() throws Exception
    {
        FtpClientHelper client = getFtpClientHelper();

        String remote = "results.html";
        URL resultsFile = getClass().getClassLoader().getResource("data/results.html");
        LOGGER.info("Loading results from {}", resultsFile);
        assertThat(client.storeFile(resultsFile, remote), is(true));

        // TODO Assert the Vfs objects

        // Should be returned by the ftp server list command
        List<String> filenames = client.listFileNames();
        assertThat(filenames, hasItem("results.html"));
    }


    @Test
    @Ignore
    public void shouldMkDir() throws IOException
    {
        FtpClientHelper client = getFtpClientHelper();
        assertThat(client.mkdir("subdir"), is(true));
        List<String> filenames = client.listFileNames();
        assertThat(filenames, hasItem("subdir"));
    }


    @Test
    @Ignore("This is only for investigation")
    public void shouldWaitForDisconnect() throws InterruptedException
    {
        LOGGER.info("Waiting for disconnect callback");
        loggingFtplet.waitForDisconnect();
    }

    @Test
    @Ignore
    public void shouldUploadFileToSubDirectory() throws Exception
    {
        FtpClientHelper client = getFtpClientHelper();

        String remoteDir = "2016/DinghyRegatta";
        String remote = remoteDir + "/results.html";
        URL resultsFile = getClass().getClassLoader().getResource("data/results.html");
        LOGGER.info("Loading results from {}", resultsFile);
        assertThat(client.storeFile(resultsFile, remote), is(true));

        // Should be returned by the ftp server list command
        List<String> filenames = client.listFileNames(remoteDir);
        assertThat(filenames, hasItem("results.html"));
    }
}