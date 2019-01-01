package com.enlightks.utility.interfaces;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author Brijesh
 *
 */
public interface LoginServiceInterface {

	public FTPClient loginFTPServer(FTPClient ftpClient)
			throws Exception ;
}
