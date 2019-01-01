package com.enlightiks.utility.model;

import javax.swing.JTextField;

public class FTPModelUtility {

	public JTextField txtDownloadPath;
	public String ftpServerName;
	public String ftpPort;
	public String ftpUserName;
	public String txtFTPPort;
	public String ftpPasswd;
	public String ftpScheduler;
	public String ftpFileUploadPath;
	public String clientName;
	public String filePattern;
	
	public FTPModelUtility() {
		// TODO Auto-generated constructor stub
	}

	public JTextField getTxtDownloadPath() {
		return txtDownloadPath;
	}

	public void setTxtDownloadPath(JTextField txtDownloadPath) {
		this.txtDownloadPath = txtDownloadPath;
	}

	public String getFtpServerName() {
		return ftpServerName;
	}

	public void setFtpServerName(String ftpServerName) {
		this.ftpServerName = ftpServerName;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getTxtFTPPort() {
		return txtFTPPort;
	}

	public void setTxtFTPPort(String txtFTPPort) {
		this.txtFTPPort = txtFTPPort;
	}

	public String getFtpPasswd() {
		return ftpPasswd;
	}

	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}

	public String getFtpScheduler() {
		return ftpScheduler;
	}

	public void setFtpScheduler(String ftpScheduler) {
		this.ftpScheduler = ftpScheduler;
	}

	public String getFtpFileUploadPath() {
		return ftpFileUploadPath;
	}

	public void setFtpFileUploadPath(String ftpFileUploadPath) {
		this.ftpFileUploadPath = ftpFileUploadPath;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	
}
