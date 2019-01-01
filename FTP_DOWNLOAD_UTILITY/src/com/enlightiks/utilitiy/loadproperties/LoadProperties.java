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
 * to Load the Properties from properties file
 * 
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
	private String enliLabel;
	private String logFilePath;
	private String imagePath;
	private String logPath;
	
	public LoadProperties(){
		log = Logger.getLogger(LoginUtility.class);
		prop = new Properties();
		loadFTPServerProperties();
		loadMailProperties();
	}
	
	/**
	 * loadFTPServerProperties
	 * load FTP Server properties from FTPConfig.properties file
	 *
	 */
	
	public void loadFTPServerProperties() {
		log.info("loadFTPServerProperties() started...");
		try {
			input = new FileInputStream(".//path.properties");
			logPath = "D://Files";
			
			// load MainPropertiesFile.properties properties file
			prop.load(input);
			String mainPath = prop.getProperty("MAINPROPERTIESPATH");
			// get image folder path
			imagePath = prop.getProperty("IMAGEPATH");
			input = new FileInputStream(mainPath);
			prop.load(input);
			String filePath = prop.getProperty("FTPCONFIG");
			// Get Enlightiks status for footer of ui
			enliLabel = prop.getProperty("ENLIGHTIKSLABEL");
			// Get log file path
			logFilePath = prop.getProperty("LOGFILE");
			input = new FileInputStream(filePath);
			// load FTPConfig.properties properties file
			prop.load(input);
			
			ftpUserName = prop.getProperty("FTPUserName");
			ftpPort = prop.getProperty("FTPServerPort");
			ftpPasswd = prop.getProperty("FTPPassword");
			ftpServerName = prop.getProperty("FTPServerName");
			log.info("loadFTPServerProperties() completed.");
		} catch (Exception e) {
			log.error("loadFTPServerProperties() Exception is : ", e);
		}
	}
	
	/**
	 * loadMailProperties
	 * load Mail properties from FTPMail.properties file
	 *
	 */
	
	public void loadMailProperties(){
		log.info("loadMailProperties() started...");
		try {
			
			input = new FileInputStream(".//Files//path.properties");
			// load MainPropertiesFile.properties properties file
			prop.load(input);
			String mailFilePath = prop.getProperty("MAINPROPERTIESPATH");
			input = new FileInputStream(mailFilePath);
			prop.load(input);
			String filePath = prop.getProperty("FTPMAILSERVICE");
			
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getEnliLabel() {
		return enliLabel;
	}

	public void setEnliLabel(String enliLabel) {
		this.enliLabel = enliLabel;
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
