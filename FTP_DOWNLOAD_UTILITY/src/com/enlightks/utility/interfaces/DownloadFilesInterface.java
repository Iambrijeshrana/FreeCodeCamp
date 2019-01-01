package com.enlightks.utility.interfaces;

import java.io.IOException;

/**
 * @author Brijesh
 *
 */
public interface DownloadFilesInterface {
	
	public void downloadFiles();
	//public void downloadFiles(String ftpFilePath) throws Exception;
	public void downloadFTPFiles() throws IOException;
}
