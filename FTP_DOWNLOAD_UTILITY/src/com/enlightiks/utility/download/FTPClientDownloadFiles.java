package com.enlightiks.utility.download;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.enlightiks.FTPUI.FTPUI;
import com.enlightiks.utilitiy.common.LoginUtility;
import com.enlightiks.utilitiy.common.MailService;
import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightiks.utility.pojo.FTPModelUtility;
import com.enlightiks.utility.pojo.SortedListModel;
import com.enlightks.utility.interfaces.DownloadFilesInterface;
import com.enlightks.utility.interfaces.LoginServiceInterface;
import com.enlightks.utility.interfaces.MailServiceInterface;

/**
 * Download FTP File from FTPServer
 * @author Brijesh
 *
 */

public class FTPClientDownloadFiles implements DownloadFilesInterface {

	private static Logger log = null;
	private boolean flag;
	private SortedListModel destListModel;
	public Border border;
	StringBuffer buffer = new StringBuffer();
	
	public FTPClientDownloadFiles() {
		log = Logger.getLogger(FTPClientDownloadFiles.class);
	}

	/**
	 * download selected ftp file from ftp server
	 */
	public void downloadFiles() {
		try {
			downloadFTPFiles();
			MailServiceInterface mailService = new MailService();
			LoadProperties loadProperties = new LoadProperties();
			final String to = loadProperties.getEmailTo();
			final String subject = loadProperties.getSubject();
			final String body = loadProperties.getBody();
			StringBuffer emailStringBuffer = new StringBuffer("<br>"+body+buffer);
			emailStringBuffer = emailStringBuffer.append("<br><br>****************************************************************");
			String emailBody = emailStringBuffer.toString();
			   mailService.sentMail(to, subject, emailBody);
		} catch (Exception e) {
		}
	}
	
	/**
	 * To download FTP File.
	 * And delete downloaded file from ftp server after downloading.
	 *
	 */
	
	public void downloadFTPFiles() throws IOException {
		log.info("downloadFTPFiles() started...");
		FTPClient ftpClient = new FTPClient();
		destListModel = new SortedListModel();
		try {
			LoginServiceInterface loginService = new LoginUtility();
			
			/**
			 * method loginFTPServer -> to login with ftp server
			 * @param ftpClient
			 * @return ftpClient
			 *
			 */ 
			ftpClient = loginService.loginFTPServer(ftpClient);
			log.info("login with ftp server is successfull.");
			// set ftp file transfer mode
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			for (final String str : FTPModelUtility.getListfinal()) {
				String[] sname = str.split("/");
				String clientName = sname[1];
				String ftpClientName = "/" + clientName;

				if (flag == false) {
					ftpClient.changeWorkingDirectory(ftpClientName);
				}
				String workingDirectory = ftpClient.printWorkingDirectory();
				if (!ftpClientName.equals(workingDirectory)) {
					ftpClient.changeWorkingDirectory(ftpClientName);
				}
				flag = true;
				FTPFile[] ftpFiles = ftpClient.listFiles(str);
				if (ftpFiles != null && ftpFiles.length > 0) {
					log.info("File available to download.");
					for (FTPFile file : ftpFiles) {
						if (!file.isFile()) 
							continue;
//						String timeStamp1 = new SimpleDateFormat(
//								"yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
						//archiveFile(ftpClient, file, timeStamp1, ftpClientName);
						// path selected by user to save the downloaded file.
						String newPath = FTPModelUtility.getFtpFileDownloadPath();
						// create client name directory into the path select by user to save downloaded file
						File file1 = new File(newPath + "\\" + clientName);
						if (!file1.exists()) {
							if (file1.mkdir()) 
								newPath = file1.getPath();
							log.info("Client name directory created.");
						} else {
							newPath = file1.getPath();
							log.info("Client name directory already exist.");
						}
						// get current date and time 
						String timeStamp = new SimpleDateFormat(
								"yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
						File downloadFilePath;
						downloadFilePath = new File(newPath + "/"+ file.getName());
						InputStream inputStream = ftpClient.retrieveFileStream(file.getName());
						// get downloaded file extension
						String ext = FilenameUtils.getExtension(file.getName());
						//add date and time to the downloaded file to remove overwriting
						OutputStream outputStream2 = new BufferedOutputStream(
								new FileOutputStream(downloadFilePath + "_"+ timeStamp+"."+ext));
						long totalBytesRead = 0;
						// get file size in byte
						long fileSize = file.getSize();
						byte[] bytesArray = new byte[4096];
						int bytesRead = -1;
						int percentCompleted = 0;
						
						long currentTime = System.currentTimeMillis();
						
						while ((bytesRead = inputStream.read(bytesArray)) != -1) {
							outputStream2.write(bytesArray, 0, bytesRead);
							totalBytesRead += bytesRead;
							percentCompleted = (int) (totalBytesRead * 100 / fileSize);
							Timer timer = new Timer((int) 0,new ActionListener() {
							    public void actionPerformed(ActionEvent e) {}
							});
							timer.setRepeats(true);
							timer.start();
							
							long endTime = System.currentTimeMillis();
							long atime = endTime-currentTime;
							long sec = (int) atime/1000;
							int newSec = (int) (sec%60);
							
							long dTime=0;
							
							String ActualRemainingTime="0";
							if(newSec != 0){
								int speed2 = (int) ((totalBytesRead*8)/(1024*sec));
								int speed1 = (int) ((totalBytesRead*8)%(1024*sec));
								String sp = speed2+"."+speed1;
								double speed = Double.parseDouble(sp);
								double ab =  (speed*(0.1220703125));
								 int size = (int) (file.getSize()/(1024));
								 dTime = (long) ((long) (size)/ab);
								 
								 long remaiSec = dTime-sec;
								 // get actual second
								 int remaSec= (int) (remaiSec%60);
								 long remaiMin = remaiSec/60;
								// get actual minute
								 int remaMin = (int) (remaiMin%60);
								// get actual hour
								 int remaHoure= (int) (remaiMin/60);
								// add '0' in prifix if value is one digit.
								 String remainingSec = remaSec+"";
								 if(remaSec < 10){
									 remainingSec = "0"+remaSec;
								 }
								 // add '0' in prifix if value is one digit.
								 String remainingMin = remaMin+"";
								 if(remaMin < 10){
									 remainingMin = "0"+remaMin;
								 }
								// add '0' in prifix if value is one digit.
								 String remainingHour = remaHoure+"";
								 if(remaHoure < 10){
									 remainingHour = "0"+remaHoure;
								 }
								 // Get Actual time in HH:MM:SS format.
								 ActualRemainingTime = remainingHour+":"+remainingMin+":"+remainingSec;
								 
							}
							if(percentCompleted == 100){
								ActualRemainingTime = "00:00:00";
							} 
							// set percentage value in the progress bar.
							FTPUI.progressBar.setValue(percentCompleted);
							border = BorderFactory.createTitledBorder(" ");
							FTPUI.progressBar.setBorder(border);
							// Get file size in MB 'file.getSize() = bytes'
							double sizeinMB = 1024 * 1024;
							double actualFileSize = file.getSize() / sizeinMB;
							// total downloading size of file in mb
							double downloadedFileSize = totalBytesRead / sizeinMB;
							String asinMB = String.format("%.2f",actualFileSize);
							String dsinMB = String.format("%.2f",downloadedFileSize);
							// to showing file name, file size, downloaded file size, and Remaining Time on ProgressBar.
							border = BorderFactory.createTitledBorder("File Name: "
											+ file.getName() + "/(" + dsinMB+ " MB of " + asinMB + " MB)"
									+" Remaining time: "+ActualRemainingTime);
							FTPUI.progressBar.setBorder(border);
							Rectangle progressRect = FTPUI.progressBar.getBounds();
							progressRect.x = 0;
							progressRect.y = 0;
							FTPUI.progressBar.paintImmediately(progressRect);
							
						}
						outputStream2.close();
						// inputStream.close();
						
						boolean success = ftpClient.completePendingCommand();
						if (success) {
							log.info("file downloaded successfully");
							try {
								Date dNow = new Date( );
							      SimpleDateFormat ft = 
							      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
								//List emailListFile = FTPModelUtility.getListfinal();
								LoadProperties loadProperties = new LoadProperties();
								String strEmail = "<br>Client Name : "+clientName
										+"<br>File Name : "+file.getName()
										+"<br>File Size : "+file.getSize()/1024
										+" kb<br>File Download Path : "+FTPModelUtility.getFtpFileDownloadPath()+"\\"+clientName
										+"<br>Email send time : "+ft.format(dNow)
										+"<br>Logs File Name : logging.log"
										+"<br>Log File Path : "+loadProperties.getLogFilePath()
										+"<br>----------------------------------------------------------------";
								buffer=buffer.append("<br>"+strEmail);
							} catch (Exception ex) {
								log.info("sentMail : ", ex);
								ex.printStackTrace();
							}
							Object obj = file.getName();
							// remove downloaded file from the list that select to download
							destListModel.removeElement(obj);
						}
						// delete file from ftp server
						//ftpClient.deleteFile(file.getName());
						log.info("downloaded file remove from ftp server");
					}
				}
			}
			log.info("downloadFTPFiles() complete.");
		} catch (Exception e) {
			log.error("downloadFiles(Properties prop) Exception is : ", e);
		} finally {
			// to logout from ftp server
			ftpClient.logout();
			log.info("logout from ftp server is successfull.");
			// disconnect ftp server
			ftpClient.disconnect();
			log.info("ftpserver disconnect.");
		}
	}

	/**
	 * 
	 * to create ftp file backup before removing 
	 * @param ftpClient
	 * @param archFile -> Actual downloaded file.
	 * @param fileuploadtimestamp -> 
	 * @param ftpPath -> Archive path.
	 * @param ftpClientName -> selected client name
	 * 
	 */ 
	public void archiveFile(FTPClient ftpClient, FTPFile archFile,
			String fileuploadtimestamp, String ftpClientName)
			throws IOException {
		log.info("archiveFile() started...");
		try {
			// to create archive directory on ftpserver in client directory
			ftpClient.makeDirectory("Archive");
			log.info("archive directory created.");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("archiveFile() creation Exception is : ", e);
		}
		String archivePath = /*ftpPath + "/" + */ftpClientName + "/Archive/"
				+ archFile.getName();
		// ftpClient.rename(sourecPath, archivePath);

		InputStream inputStream = ftpClient.retrieveFileStream(archFile
				.getName());
		OutputStream outputStream2 = new BufferedOutputStream(
				new FileOutputStream(archivePath));
		byte[] bytesArray = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(bytesArray)) != -1) {
			outputStream2.write(bytesArray, 0, bytesRead);
		}
		outputStream2.close();
		inputStream.close();

		log.info("archivefile() complete");
	}
}
