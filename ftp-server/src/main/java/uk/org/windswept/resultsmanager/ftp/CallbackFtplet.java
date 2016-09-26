package uk.org.windswept.resultsmanager.ftp;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.windswept.common.ToString;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class CallbackFtplet extends DefaultFtplet
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackFtplet.class);
    private List<DisconnectCallback> disconnectCallbackList = newArrayList();
    private List<UploadStartCallback> uploadStartCallbackList = newArrayList();
    private List<UploadEndCallback> uploadEndCallbackList = newArrayList();

    public CallbackFtplet()
    {
        super();
    }

    @Override
    public void init (FtpletContext ftpletContext) throws FtpException
    {
        super.init(ftpletContext);
        LOGGER.debug("init ftpletContext:{}", ftpletContext);
    }

    @Override
    public void destroy ()
    {
        super.destroy();
        LOGGER.debug("destroy");

    }

    @Override
    public FtpletResult onConnect (FtpSession session) throws FtpException, IOException
    {
        LOGGER.debug("onConnect session:{}", new ToString(session));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onDisconnect (FtpSession session) throws FtpException, IOException
    {
        LOGGER.debug("onDisconnect session:{}", new ToString(session));
        disconnectCallbackList.forEach(p -> p.onDisconnect());
        return null;
    }

    @Override
    public FtpletResult onLogin (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onLogin session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onDeleteStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onDeleteStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onDeleteEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onDeleteEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onUploadStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onUploadStart session:{} request:{}", new ToString(session), new ToString(request));
        uploadStartCallbackList.forEach(p -> p.onUploadStart(session.getUser().getHomeDirectory(), request.getArgument()));
        return null;
    }

    @Override
    public FtpletResult onUploadEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onUploadEnd session:{} request:{}", new ToString(session), new ToString(request));
        uploadEndCallbackList.forEach(p -> p.onUploadEnd(session.getUser().getHomeDirectory(), request.getArgument()));
        return null;
    }

    @Override
    public FtpletResult onDownloadStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onDownloadStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onDownloadEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onDownloadEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onRmdirStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onRmdirStart session:{} request:{}", new ToString(session), request);
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onRmdirEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onRmdirEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onMkdirStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onMkdirStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onMkdirEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onMkdirEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onAppendStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onAppendStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onAppendEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onAppendEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onUploadUniqueStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onUploadUniqueStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onUploadUniqueEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onUploadUniqueEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onRenameStart (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onRenameStart session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onRenameEnd (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onRenameEnd session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    @Override
    public FtpletResult onSite (FtpSession session, FtpRequest request) throws FtpException, IOException
    {
        LOGGER.debug("onSite session:{} request:{}", new ToString(session), new ToString(request));
        // TODO add callback functionality
        return null;
    }

    public void addDisconnectCallback(DisconnectCallback disconnectCallback)
    {
        disconnectCallbackList.add(disconnectCallback);
    }

    public void addUploadStartCallback(UploadStartCallback uploadStartCallback)
    {
        uploadStartCallbackList.add(uploadStartCallback);
    }

    public void addUploadEndCallback(UploadEndCallback uploadEndCallback)
    {
        uploadEndCallbackList.add(uploadEndCallback);
    }
}
