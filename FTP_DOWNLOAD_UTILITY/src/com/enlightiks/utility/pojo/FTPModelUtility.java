package com.enlightiks.utility.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

public final class FTPModelUtility {

	public static String ftpFileDownloadPath;
	public static String directoryName;
	public static List<String> listfinal = new ArrayList<String>();
	public static JTextField txtDownloadPath;
	public static List<String> destList = new ArrayList<String>();
	public static List<String> clientFileList = new ArrayList<String>();
	public static Set<String> fileExtList = new HashSet<String>();
	
	public static Set<String> getFileExtList() {
		return fileExtList;
	}
	public static void setFileExtList(Set<String> fileExtList) {
		FTPModelUtility.fileExtList = fileExtList;
	}
	public static List<String> getClientFileList() {
		return clientFileList;
	}
	public static void setClientFileList(List<String> clientFileList) {
		FTPModelUtility.clientFileList = clientFileList;
	}
	public static List<String> getDestList() {
		return destList;
	}
	public static void setDestList(List<String> destList) {
		FTPModelUtility.destList = destList;
	}
	private FTPModelUtility(){
		
	}
	public static String getFtpFileDownloadPath() {
		return ftpFileDownloadPath;
	}
	public static void setFtpFileDownloadPath(String fileDownloadPath) {
		ftpFileDownloadPath = fileDownloadPath;
	}
	public static String getDirectoryName() {
		return directoryName;
	}
	public static void setDirectoryName(String directryName) {
		directoryName = directryName;
	}
	public static List<String> getListfinal() {
		return listfinal;
	}
	public static void setListfinal(List<String> listfinal) {
		FTPModelUtility.listfinal = listfinal;
	}
	public static JTextField getTxtDownloadPath() {
		return txtDownloadPath;
	}
	public static void setTxtDownloadPath(JTextField txtDownloadPath) {
		FTPModelUtility.txtDownloadPath = txtDownloadPath;
	}
	
}
