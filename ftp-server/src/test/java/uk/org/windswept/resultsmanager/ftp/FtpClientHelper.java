package uk.org.windswept.resultsmanager.ftp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by 802998369 on 26/09/2016.
 */
public class FtpClientHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpClientHelper.class);

    private final FTPClient client ;

    public FtpClientHelper ()
    {
        client = new FTPClient();
    }

    public FtpClientHelper (InetAddress address, int port, String username, String password) throws IOException
    {
        this();
        connect(address, port);
        login(username, password);
    }

    public FtpClientHelper connect(InetAddress address, int port) throws IOException
    {
        client.connect(address, port);
        return this;
    }

    public FtpClientHelper login(String username, String password) throws IOException
    {
        client.login(username, password);
        return this;
    }

    public List<String> listFileNames() throws IOException
    {
        return listFileNames(null);
    }

    public List<String> listFileNames(String path) throws IOException
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

    public boolean storeFile(URL local, String remote) throws IOException
    {
        return client.storeFile(remote, local.openStream());
    }
}
