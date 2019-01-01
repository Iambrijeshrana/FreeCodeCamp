package com.enlightiks.utilitiy.common;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightks.utility.interfaces.DecryptInterface;
import com.enlightks.utility.interfaces.LoginServiceInterface;

/**
 * Login with FTPServer
 * @author Brijesh
 *
 */
public final class LoginUtility implements LoginServiceInterface{

	private static Logger log = null;
	private LoadProperties loadProperties;
	
	public LoginUtility(){
		log = Logger.getLogger(LoginUtility.class);
		loadProperties = new LoadProperties();
	}
	
	/**
	 * To Login with FTPServer
	 * @param ftpClient
	 * @return ftpClient
	 * @throws Exception
	 * 
	 */
	public FTPClient loginFTPServer(FTPClient ftpClient)
			throws Exception {
		log.info("loginFTPServer() started... ");
		ftpClient = new FTPClient();
		try {
			DecryptInterface decryptInterface = new DecryptionUtility();
			// Get Decrypted Server Name.
			final String ftpServerName = decryptInterface.decryptedServerName();
			// Get Decrypted UserName of ftpserver
			final String ftpUser = decryptInterface.decryptedUsername();
			//Get Decrypted password of ftpserver
			final String ftpPass = decryptInterface.decryptedPassword();
			// get port no of ftpserver
			final String ftpPort = loadProperties.getFtpPort();
			// connect with ftpserver
			ftpClient.connect(ftpServerName, Integer.parseInt(ftpPort));
			//Login with ftpserver by passing UserName and Password
			ftpClient.login(ftpUser, ftpPass);
			// to enter into Active mode.
			ftpClient.enterLocalActiveMode();
			log.info("login with ftpserver successfull");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("loginFTPServer Exception is : ", e);
		}
		log.info("loginFTPServer() complete. ");
		// return ftpClient
		return ftpClient;
	}
}