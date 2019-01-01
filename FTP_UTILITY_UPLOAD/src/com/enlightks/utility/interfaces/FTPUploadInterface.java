package com.enlightks.utility.interfaces;

import java.io.File;

/**
 * @author Brijesh
 *
 */

public interface FTPUploadInterface extends Runnable{

	public void ftpUploadFiles();
	//public void ftpUploadFiles(String ftpFilePath);
	public void ftpServerUploadFiles();
	public void archivefile(File archFile,String fileuploadtimestamp);
}
