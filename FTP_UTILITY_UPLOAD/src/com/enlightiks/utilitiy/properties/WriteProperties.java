/**
 * 
 */
package com.enlightiks.utilitiy.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * To write the values provided by user on ui in a properties file.
 * 
 * @author Brijesh
 *
 */
public class WriteProperties {
	private static Logger log = null;
	
	public WriteProperties() {
		log = Logger.getLogger(WriteProperties.class);
	}

	/**
	 * writeInProperertiesFile
	 *create propeties file with provided value
	 *@param Value from the ui
	 *@throws IOException, Exception
	 */
	public void writeInProperertiesFile(String ftpPort, String ftpScheduler,
			String clientName, String ftpFileUploadPath, String filePattern,
			String ftpServerName, String ftpUserName, String ftpPasswd)
			throws IOException, Exception {
		log.info("writeInProperertiesFile() started...");
		Properties prop = new Properties();
		OutputStream output = null;
		EncryptionUtil cryptoUtil = new EncryptionUtil();
		try {
			// encrypt the server name, ftp user name and ftp password
			log.info("getEncryptedValues() calling.");
			cryptoUtil.getEncryptedValues(ftpServerName, ftpUserName, ftpPasswd);
			// Get encrypted ftp password
			String ftpPassword = cryptoUtil.encryptedPassword();
			log.info("encrypted password recieved.");
			//get encrypted ftp user name
			String ftpUser = cryptoUtil.encryptedUsername();
			log.info("encrypted ftpUser recieved.");
			// Get encrypted server name
			String ftpServer = cryptoUtil.encryptedServerName();
			log.info("encrypted ftpServer recieved.");
			// Get file path
			output = new FileOutputStream("properties//FTPConfig.properties");
			// write encrypted server name
			prop.setProperty("FTPServerName", ftpServer);
			// write ftp port number
			prop.setProperty("FTPServerPort", ftpPort);
			// write encrypted user name
			prop.setProperty("FTPUserName", ftpUser);
			// write encrypted ftp password
			prop.setProperty("FTPPassword", ftpPassword);
			// write shedular
			prop.setProperty("FTPScheduler", ftpScheduler);
			// write client name
			prop.setProperty("FTPClientName", clientName);
			// write path from where we upload the file
			prop.setProperty("FTPFileUploadPath", ftpFileUploadPath);
			// write file pattern
			prop.setProperty("FTPFilePattern", filePattern);

			// save properties to project properties folder
			prop.store(output, null);
			log.info("All values written in file successfully.");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					log.error("writeInProperertiesFile() Exception is : ", e);
				}
			}

		}
		log.info("writeInProperertiesFile() complete.");
	}
	
	/**
	 *writeInMainProperertiesFile
	 *@throws IOException, Exception
	 */
	public void writeInMainProperertiesFile(){
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		try {
			input = new FileInputStream("properties//MainProperties.properties");
			prop.load(input);
			String ftpFilePath = prop.getProperty("FILEPATH");
			String mailFilePath = prop.getProperty("MAILFILEPATH");
			String loFilePath = prop.getProperty("LOGFILEPATH");
			output = new FileOutputStream("properties//MainProperties.properties");
			prop.setProperty("RECONFIG", "N");
			prop.setProperty("FILEPATH", ftpFilePath);
			prop.setProperty("MAILFILEPATH", mailFilePath);
			prop.setProperty("LOGFILEPATH", loFilePath);
			// save properties to project properties folder
			prop.store(output, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException io) {
			io.printStackTrace();
		}  finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					log.error("writeInProperertiesFile() Exception is : ", e);
				}
			}

		}
	}
}