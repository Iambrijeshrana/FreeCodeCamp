package com.enlightiks.utility.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.common.LoginUtility;
import com.enlightks.utility.interfaces.DirectoryNameInterface;
import com.enlightks.utility.interfaces.LoginServiceInterface;

/**
 * Download FTP File from FTPServer
 * @author Brijesh
 *
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FTPClientsName implements DirectoryNameInterface {

	private static Logger log = null;

	public FTPClientsName() {
		log = Logger.getLogger(FTPClientsName.class);
	}

	/**
	 * return list of client name from ftp
	 */
	public List<String> getDirectoryNames() {
		log.info("getDirectoryNames() started..."); 
		List list = new ArrayList<String>();
		try {
			list = getFTPDirectoryNames();
			log.info("getDirectoryNames() complete."); 
		} catch (IOException e) {
			log.error("getDirectoryNames() Exception is :", e); 
		}
		log.info("getDirectoryNames() return client list."); 
		return list;
	}

	/**
	 * Get Client name from FTPServer.
	 * 
	 * @return List of client Available in FTP
	 *
	 */

	public List<String> getFTPDirectoryNames() throws IOException {
		log.info("getFTPDirectoryNames() started..."); 
		FTPClient ftpClient = null;
		List<String> list = new ArrayList<String>();
		try {
			LoginServiceInterface loginService = new LoginUtility();
			
			/**
			 * method loginFTPServer -> to login with ftp server.
			 * @param ftpClient.
			 * @return ftpClient.
			 *
			 */
			ftpClient = loginService.loginFTPServer(ftpClient);
			log.info("login with ftp server is successfull."); 
			ftpClient.changeWorkingDirectory("");
			FTPFile[] ftpDirectory = ftpClient.listDirectories();
			//get file name and add into list
			if (ftpDirectory != null && ftpDirectory.length > 0) {
				log.info("client available on ftp server."); 
				for (FTPFile str : ftpDirectory) {
					String[] s = str.toString().split(" ");
					list.add(s[19]);
				}
			} 
			// show popup message if no client available in ftp.
			else {
				log.info("popup message because no client available on ftp."); 
				JOptionPane.showMessageDialog(null, "No Client Available",
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			log.error("getDirectoryNames(Properties prop) Exception is : ", e);
		}
		// logout from ftp server
		ftpClient.logout();
		log.info("ftp server logout."); 
		
		// disconnect ftp server
		ftpClient.disconnect();
		log.info("ftp server disconnect."); 
		
		//return list of client
		log.info("getFTPDirectoryNames() complete."); 
		return list;
	}
}