package uk.org.windswept.resultsmanager.ftp;

import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.windswept.common.ToString;

import java.io.IOException;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class LoggingFtplet extends DefaultFtplet
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFtplet.class);
    private DisconnectCallback disconnectCallback;

    private Object disconnectSemaphore = new Object();

    public LoggingFtplet ()
    {
        super();
    }

    @Override
    public void init (FtpletContext ftpletContext) throws FtpException
    {
        super.init(ftpletContext);
        LOGGER.info("init ftpletContext:{}", ftpletContext);
    }

    @Override
    public void destroy ()
    {
        super.destroy();
        LOGGER.info("destroy");

    }

    @Override
    public FtpletResult onConnect (FtpSession session) throws FtpException, IOException
    {
        LOGGER.info("onConnect session:{}", new ToString(session));
        return super.onConnect(session);
    }

    @Override
    public FtpletResult onDisconnect (FtpSession session) throws FtpException, IOException
    {
        LOGGER.info("onDisconnect session:{}", new ToString(session));
        fireNotify(disconnectSemaphore);
        return super.onDisconnect(session);
    }

    @Override
    public FtpletResult beforeCommand (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("beforeCommand session:{} request:{}", new ToString(session), new ToString(request));
        return super.beforeCommand(session, request);
    }

    @Override
    public FtpletResult afterCommand (FtpSession session, FtpRequest request, FtpReply reply) throws FtpException,
                                                                                                     IOException
    {
        LOGGER.info("afterCommand session:{} request:{} reply:{}", new ToString(session), new ToString(request), new ToString(reply));
        return super.afterCommand(session, request, reply);
    }

    @Override
    public FtpletResult onLogin (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onLogin session:{} request:{}", new ToString(session), new ToString(request));
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onDeleteStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onDeleteStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onDeleteStart(session, request);
    }

    @Override
    public FtpletResult onDeleteEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onDeleteEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onDeleteEnd(session, request);
    }

    @Override
    public FtpletResult onUploadStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onUploadStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onUploadStart(session, request);
    }

    @Override
    public FtpletResult onUploadEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onUploadEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onUploadEnd(session, request);
    }

    @Override
    public FtpletResult onDownloadStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onDownloadStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onDownloadEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onDownloadEnd(session, request);
    }

    @Override
    public FtpletResult onRmdirStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onRmdirStart session:{} request:{}", new ToString(session), request);
        return super.onRmdirStart(session, request);
    }

    @Override
    public FtpletResult onRmdirEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onRmdirEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onRmdirEnd(session, request);
    }

    @Override
    public FtpletResult onMkdirStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onMkdirStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onMkdirStart(session, request);
    }

    @Override
    public FtpletResult onMkdirEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onMkdirEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onMkdirEnd(session, request);
    }

    @Override
    public FtpletResult onAppendStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onAppendStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onAppendStart(session, request);
    }

    @Override
    public FtpletResult onAppendEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onAppendEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onAppendEnd(session, request);
    }

    @Override
    public FtpletResult onUploadUniqueStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onUploadUniqueStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onUploadUniqueStart(session, request);
    }

    @Override
    public FtpletResult onUploadUniqueEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onUploadUniqueEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onUploadUniqueEnd(session, request);
    }

    @Override
    public FtpletResult onRenameStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onRenameStart session:{} request:{}", new ToString(session), new ToString(request));
        return super.onRenameStart(session, request);
    }

    @Override
    public FtpletResult onRenameEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onRenameEnd session:{} request:{}", new ToString(session), new ToString(request));
        return super.onRenameEnd(session, request);
    }

    @Override
    public FtpletResult onSite (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.info("onSite session:{} request:{}", new ToString(session), new ToString(request));
        return super.onSite(session, request);
    }

    private void fireNotify(Object semaphore)
    {
        synchronized(semaphore)
        {
            semaphore.notify();
        }
    }

    public void waitForDisconnect() throws InterruptedException
    {
        synchronized(disconnectSemaphore)
        {
            disconnectSemaphore.wait();
        }
    }

}
