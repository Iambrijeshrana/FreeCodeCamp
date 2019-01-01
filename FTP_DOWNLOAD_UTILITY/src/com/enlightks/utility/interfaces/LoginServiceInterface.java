package com.enlightks.utility.interfaces;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author Brijesh
 *
 */
public interface LoginServiceInterface {

	public FTPClient loginFTPServer(FTPClient ftpClient)
			throws IOException, Exception;
}
