package uk.org.windswept.resultsmanager.ftp;

/**
 * Created by 802998369 on 26/09/2016.
 */
public interface UploadStartCallback
{
    void onUploadStart(String userHomeDirectory, String file);
}
