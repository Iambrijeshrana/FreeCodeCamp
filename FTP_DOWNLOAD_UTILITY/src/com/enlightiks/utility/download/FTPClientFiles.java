package com.enlightiks.utility.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.common.LoginUtility;
import com.enlightiks.utility.pojo.FTPModelUtility;
import com.enlightks.utility.interfaces.FileNameInterface;
import com.enlightks.utility.interfaces.LoginServiceInterface;

/**
 * Get FTP File from FTPServer
 * 
 * @author Brijesh
 *
 */

public class FTPClientFiles implements FileNameInterface {

	private static Logger log = null;
	
	public FTPClientFiles() {
		log = Logger.getLogger(FTPClientFiles.class);
	}

	/**
	 * @return List of file for selected client.
	 */
	public List<String> getFilesName() {
		log.info("getFilesName() started...");
		List<String> getFilesList = new ArrayList<String>();
		try {
			getFilesList = getAllFTPFiles();
			log.info("getFilesName() complete.");
		} catch (Exception e) {
			log.error("getFilesName() Exception is : ", e);
		}
		log.info("getFilesName() return list of file");
		return getFilesList;
	}

	/**
	 * To get the List of file from FTPServer
	 * for selected Client
	 * @return List of FTP File.
	 *
	 */
	public List<String> getAllFTPFiles() throws IOException {
		log.info("getAllFTPFiles() started...");
		FTPClient ftpClient = new FTPClient();
		String ftpClientName = FTPModelUtility.getDirectoryName();
		Set<String> list = new LinkedHashSet<String>();
		List<String> list1 = new ArrayList<String>();
		try {
			LoginServiceInterface loginService = new LoginUtility();
			
			/**
			 * method loginFTPServer
			 * @param ftpClient
			 * @return ftpClient
			 *
			 */ 
			ftpClient = loginService.loginFTPServer(ftpClient);
			log.info("login with ftp server is successfull.");
			ftpClient.changeWorkingDirectory(ftpClientName);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			if (ftpFiles != null && ftpFiles.length > 0) {
				log.info("Files available for selected client.");
				for (FTPFile file : ftpFiles) {
					if (!file.isFile()) {
						continue;
					}
					
					// Get all File extension from selected client
					String ext = FilenameUtils.getExtension(file.getName());
					
					list1.add("/" + ftpClientName + "/" + file.getName());
					list.add(ext);
					// set file extension into a set.
					FTPModelUtility.setFileExtList(list);
				}
			} 
			// show popup message if no file available for selected client
			else {
				log.info("popup message becouse no file available for selected client.");
				JOptionPane.showMessageDialog(null,
						"No File Available for Client : " + ftpClientName,
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			log.error("getAllFilesName(Properties prop) Exception is : ", e);
		} finally {
			//logout from ftpserver
			ftpClient.logout();
			log.info("logout from ftpserver");
			
			//disconnect ftpserver
			ftpClient.disconnect();
			
			log.info("ftpserver disconnect.");
			log.info("getAllFTPFiles() starcomplete...");
		}
		//return list of file
		log.info("getAllFTPFiles() return list of file.");
		return list1;
	}

}