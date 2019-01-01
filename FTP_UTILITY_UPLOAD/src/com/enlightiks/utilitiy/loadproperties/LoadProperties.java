/**
 * 
 */
package com.enlightiks.utilitiy.loadproperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.common.LoginUtility;

/**
 * to Load the ftp server and 
 * ftp mail Properties from properties file
 * @author Brijesh
 *
 */
public final class LoadProperties {
	
	private static Logger log = null;
	private Properties prop;
	private InputStream input = null;
	private String ftpUserName;
	private String ftpPasswd;
	private String ftpPort;
	private String ftpServerName;
	private String hostAddress;
	private String portAddress;
	private String fromEmail;
	private String password;
	private String portNumber;
	private String emailTo;
	private String subject;
	private String body;
	private String ftpScheduler; 
	private String clientName; 
	private String ftpFileUploadPath; 
	private String filePattern;
	private String reconfig;
	private String fileSize;
	private String fileFormat;
	private String enliLabel;
	private String logPath;
	
	public LoadProperties(){
		log = Logger.getLogger(LoginUtility.class);
		prop = new Properties();
		loadFTPServerProperties();
		loadMailProperties();
	}
	
	/**
	 * loadMainProperties
	 * load Main properties File
	 * To Reconfigure the ui
	 * @return Y or N -> Y - reconfig to the ui
	 */
	public String loadMainProperties() {
		try {
			input = new FileInputStream("properties//MainProperties.properties");
			prop.load(input);
			reconfig = prop.getProperty("RECONFIG");
			System.out.println(reconfig);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reconfig;
	}
	
	/**
	 * loadFTPServerProperties
	 * load FTP Server properties from FTPConfig.properties file
	 * Through MainProperties.propeties file.
	 */
	public void loadFTPServerProperties() {
		log.info("loadFTPServerProperties() started...");
		try {
			input = new FileInputStream("properties//MainProperties.properties");
			// load MainPropertiesFile.properties properties file
			prop.load(input);
			String filePath = prop.getProperty("FILEPATH");
			// get log file path
			logPath = prop.getProperty("LOGPATH");
			// to get file size
			fileSize = prop.getProperty("ZIPFILESIZE");
			// Get selected file format from Main Properties file.
			fileFormat = prop.getProperty("FILEFORMAT");
			// Get Enlightiks status for footer of ui
			enliLabel = prop.getProperty("ENLIGHTIKSLABEL");
			
			input = new FileInputStream(filePath);
			// load FTPConfig.properties properties file
			prop.load(input);
			// Get shedular value
			ftpScheduler = prop.getProperty("FTPScheduler");
			// Get Client name
			clientName = prop.getProperty("FTPClientName");
			// get path
			ftpFileUploadPath = prop.getProperty("FTPFileUploadPath");
			//Get file pattern
			filePattern = prop.getProperty("FTPFilePattern");
			//Get ftp user name
			ftpUserName = prop.getProperty("FTPUserName");
			// Get ftp port no.
			ftpPort = prop.getProperty("FTPServerPort");
			// Get ftp password
			ftpPasswd = prop.getProperty("FTPPassword");
			//Get ftp server name
			ftpServerName = prop.getProperty("FTPServerName");
			log.info("loadFTPServerProperties() completed.");
		} catch (Exception e) {
			log.error("loadFTPServerProperties() Exception is : ", e);
		}
	}
	
	/**
	 * loadMailProperties
	 * load Mail properties from FTPMail.properties file
	 *T hrough MainProperties.propeties file.
	 */
	public void loadMailProperties(){
		log.info("loadMailProperties() started...");
		try {
			
			input = new FileInputStream("properties//MainProperties.properties");
			// load MainPropertiesFile.properties properties file
			prop.load(input);
			String filePath = prop.getProperty("MAILFILEPATH");
			
			input = new FileInputStream(filePath);
			// load FTPMail.properties file
			prop.load(input);
			hostAddress = prop.getProperty("hostAddress");
			portAddress = prop.getProperty("portAddress");
			fromEmail = prop.getProperty("fromEmail");
			password = prop.getProperty("password");
			portNumber = prop.getProperty("portNumber");
			emailTo = prop.getProperty("EmailTo");
			subject = prop.getProperty("subject");
			body = prop.getProperty("body");
			log.info("loadMailProperties() completed...");
		} catch (FileNotFoundException e) {
			log.error("loadMailProperties() Exception is :", e);
		}catch (IOException e) {
			log.error("loadMailProperties() Exception is : ", e);
		}
	}
	
	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getEnliLabel() {
		return enliLabel;
	}

	public void setEnliLabel(String enliLabel) {
		this.enliLabel = enliLabel;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getReconfig() {
		return reconfig;
	}

	public void setReconfig(String reconfig) {
		this.reconfig = reconfig;
	}

	public String getFtpScheduler() {
		return ftpScheduler;
	}

	public void setFtpScheduler(String ftpScheduler) {
		this.ftpScheduler = ftpScheduler;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getFtpFileUploadPath() {
		return ftpFileUploadPath;
	}

	public void setFtpFileUploadPath(String ftpFileUploadPath) {
		this.ftpFileUploadPath = ftpFileUploadPath;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpServerName() {
		return ftpServerName;
	}
	public void setFtpServerName(String ftpServerName) {
		this.ftpServerName = ftpServerName;
	}
	public String getFtpPasswd() {
		return ftpPasswd;
	}
	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}


	public String getHostAddress() {
		return hostAddress;
	}


	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}


	public String getPortAddress() {
		return portAddress;
	}


	public void setPortAddress(String portAddress) {
		this.portAddress = portAddress;
	}


	public String getFromEmail() {
		return fromEmail;
	}


	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPortNumber() {
		return portNumber;
	}


	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}


	public String getEmailTo() {
		return emailTo;
	}


	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}

}
