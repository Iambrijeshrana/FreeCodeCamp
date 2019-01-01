/**
 * 
 */
package com.enlightiks.utility.FTP;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.common.LoginUtility;
import com.enlightiks.utilitiy.common.MailService;
import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightiks.utility.zip.ZipCreation;
import com.enlightks.utility.interfaces.FTPUploadInterface;
import com.enlightks.utility.interfaces.LoginServiceInterface;
import com.enlightks.utility.interfaces.MailServiceInterface;

/**
 * To upload the file on ftp server
 * 
 * @author Brijesh
 *
 */
public class FTPUpload implements FTPUploadInterface {

	boolean mail = true;
	Thread ftpUploadThread = null;
	private static Logger log = null;
	private LoadProperties loadProperties;
	OutputStream outputStream = null;
	private static final int BUFFER_SIZE = 4096;
	ZipCreation creation;
	public StringBuffer buffer = new StringBuffer();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log = Logger.getLogger(FTPUpload.class);
		log.info("FTPUpload -> main() started...");
		FTPUpload ftpUpload = new FTPUpload();
		try {
			log.info("ftpUploadFiles() calling..");
			ftpUpload.ftpUploadFiles();
		} catch (Exception e) {
			log.error("FTPUpload:main Exception is : ", e);
		}
	}

	public FTPUpload() {
		loadProperties = new LoadProperties();
		creation = new ZipCreation();
	}

	/**
	 * ftpUploadFiles To create a thread
	 */
	public void ftpUploadFiles() {
		log.info("FTPUpload Invoking Constructor with Param Value ");
		try {
			ftpUploadThread = new Thread(this);
			ftpUploadThread.start();
		} catch (Exception e) {
			log.error("ftpUploadFiles() Exception is : ", e);
		}
	}

	/**
	 * run Automatically executed after particular time
	 */
	public void run() {
		try {
			while (ftpUploadThread != null) {
				// new LoadProperties();
				ftpServerUploadFiles();
				String shedular = loadProperties.getFtpScheduler();
				Thread.sleep(Long.parseLong(shedular));
			}
		} catch (Exception e) {
			log.error("Thread run exception is : ", e);
		}
	}

	/**
	 * ftpServerUploadFiles Upload files from user define path to ftp server
	 * delete uploaded file from the main path
	 */
	public void ftpServerUploadFiles()
	{
		log.info("ftpServerUploadFiles() started...");
		FTPClient ftpClient=null;
		try
		{
				ftpClient = new FTPClient();
				// Login with ftp server
				LoginServiceInterface loginUtilityInterface = new LoginUtility();
				ftpClient = loginUtilityInterface.loginFTPServer(ftpClient);
				// Get path from where upload the file
				final String filePath=loadProperties.getFtpFileUploadPath();
				// Get file format to upload the file
				final String filePattern=loadProperties.getFilePattern();
				File dir=new File(filePath);
				// create archive directory for archive file
				File archDir=new File(filePath+"/Archive");
				archDir.mkdir();
				// Get all file of provided file format
				FileFilter fileFilter=new WildcardFileFilter("*."+filePattern);
				File [] files=dir.listFiles(fileFilter);
				if(files.length <= 0)
				{
					Date dNow = new Date( );
				      SimpleDateFormat ft = 
				      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
					log.info("uploadfile NO files available to process");
					System.out.println("NO files available to process at "+ft.format(dNow));
					String ftpTime = loadProperties.getFtpScheduler();
					int tStamp = Integer.parseInt(ftpTime);
					int timeStamp = tStamp/1000;
					int timeSt = timeStamp+1;
					System.out.println("Time Stamp : "+timeSt+" second.");
					log.info("Time Stamp : "+timeStamp+" second.");
					//System.exit(0);
				}
				String remotepushFilename="";
				// Get ftp server client name to upload the file
				final String directory=loadProperties.getClientName();
				// create client name directory on ftp server
				ftpClient.makeDirectory(directory);
				ftpClient.changeWorkingDirectory(directory);
				for(int filecntr=0;filecntr< files.length;filecntr++){	
					// Get file size to convert in zip format.
					String fileSetSize = loadProperties.getFileSize();
					// convert string into integer value.
					int setSize = Integer.parseInt(fileSetSize);
					String fName="";
					File zipFile = null;
					long fileSize = files[filecntr].length();
					long actualFileSizeinMB = fileSize/(1024*1024);
		            if(actualFileSizeinMB > setSize){
		            	String fileName = files[filecntr].getName();
		            	fName = creation.createZip(filePath, fileName);
		            	zipFile = new File(filePath+"/"+fName);
		            	fileSize = zipFile.length();
		            }
					String fileuploadtimestamp=String.valueOf(new java.util.Date().getTime());
					archivefile(files[filecntr],fileuploadtimestamp);
					// check file exist or not and return true or false
					boolean check = new File(filePath, fName).exists();
					if(actualFileSizeinMB <= setSize || check){
					FileInputStream inputStream = null;
					// for converted zip file 
					if(actualFileSizeinMB > setSize){
						remotepushFilename=zipFile.getName();
						inputStream = new FileInputStream(zipFile);
					}
					// for Actual file
					else{
						remotepushFilename=files[filecntr].getName();
						inputStream = new FileInputStream(files[filecntr]);
					}
					
					outputStream = ftpClient.storeFileStream(remotepushFilename);
					byte[] buffer = new byte[BUFFER_SIZE];
		            int bytesRead = -1;
		            long totalBytesRead = 0;
		            int percentCompleted = 0;
		           
		           // long actualFileSize = files[filecntr].length();
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		            	outputStream.write(buffer, 0, bytesRead);
		                totalBytesRead += bytesRead;
		                percentCompleted = (int) ((totalBytesRead * 100) / fileSize);
		                System.out.println("File Name : "+files[filecntr]);
		                System.out.println("percentCompleted : "+percentCompleted+"%");
		                
		            }
		            
		            Date dNow = new Date( );
				      SimpleDateFormat ft = 
				      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
				      String emailBody = "<br> File Name : "+files[filecntr]
          				+"<br>File Size : "+fileSize/1024
          				+" kb<br>FTP Path Uploaded : "+remotepushFilename
          				+"<br>Email send time : "+ft.format(dNow)
          				+"<br>Logs File Name : logging.log"
          				+"<br>Logs Path : "+loadProperties.getLogPath()
          				+"<br>Archive File Name : "+remotepushFilename+"_"+fileuploadtimestamp
          				+"<br>Archive Path : "+archDir+"//"+remotepushFilename+"_"+fileuploadtimestamp
          				+"<br>----------------------------------------------------------------";
          		StringBuffer stringBuffer = new StringBuffer();
          		stringBuffer= stringBuffer.append(emailBody);
          		//this.buffer= this.buffer.append(emailBody);
          		
		            if(mail){
		            	try{
		            	final String body = loadProperties.getBody();
		            	//StringBuffer sb = new StringBuffer("<br>"+body+"<br>"+stringBuffer);
		            	//stringBuffer = stringBuffer.append("<br>"+body+"<br>"+stringBuffer);
		            	stringBuffer = stringBuffer.append("<br><br>****************************************************************");
		        		StringBuffer s = new StringBuffer();
		        		s=s.append("<br>"+body+"<br>"+emailBody);
		        		s = s.append("<br><br>****************************************************************");
		            	String actualEmailBody = s.toString();
		            	// Get email id to send mail
						final String to = loadProperties.getEmailTo();
						// Get email subject
						final String subject = loadProperties.getSubject();
						// get msg
						
						MailServiceInterface mailService = new MailService();
						// sent mail
						mailService.sentMail(to, subject, actualEmailBody);
						log.info("Mail sent.");
		            	}catch(Exception e){
		            		log.error("Mail Exception is : ", e);
		            	}
		            }
		            mail=false;
		            inputStream.close();
		            outputStream.close();
		            ftpClient.completePendingCommand();
		            files[filecntr].delete();
		            zipFile.delete();
				}
				}
		}
		catch(Exception e)
		{
			log.error("ftpServerUploadFiles() exception is :  ",e);
		}
		finally
		{
			try{
				if(ftpClient.isConnected())
				{
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}
			 catch (Exception ex) {
				 log.error("ftpServerUploadFiles() :Error in FTP Disconnection ",ex);
				}
			
		}
		log.info("ftpServerUploadFiles() complete.");
	}

	/**
	 * archivefile create Uploaded files backup before uploading the file.
	 */
	public void archivefile(File archFile, String fileuploadtimestamp) {
		log.info("archive() started...");
		Path sourcePath = Paths.get(archFile.getPath());
		Path archivePath = Paths.get(archFile.getParent(),
		
		"Archive", archFile.getName() + "_" + fileuploadtimestamp);

		try {
			System.out.println("archFile : "+archFile);
			// file in archive directory
			Files.copy(sourcePath, archivePath);
			log.info("archive file copyed");
		} catch (IOException e1) {
			log.error("archivefile() exception is : ", e1);
		}
		log.info("archivefile() Archving completed");
	}

}
