package com.enlightiks.utility.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.loadproperties.LoadProperties;
 
/**
 * To convert the uploaded file in zip format.
 * @author Brijesh
 *
 */
public class ZipCreation 
{	
	private static Logger log = null;
	LoadProperties loadProperties;
	
	public ZipCreation() {
		log = Logger.getLogger(ZipCreation.class);
		loadProperties = new LoadProperties();
	}
	/**
	 * @param uploaded file path
	 * @param uploaded file name
	 * @return create zip file name
	 */
	public String createZip(String filePath, String fileName){
		
		log.info("createZip() started...");
    	byte[] buffer = new byte[1024];
    	// created zip file name.
    	String fileFormat = loadProperties.getFileFormat();
    	String fName = fileName+"."+fileFormat;
    	try{
    		// To create the file in zip format.
    		FileOutputStream fos = new FileOutputStream(filePath+"/"+fileName+"."+fileFormat);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		// file name inside the zip folder.
    		ZipEntry ze= new ZipEntry(fileName);
    		zos.putNextEntry(ze);
    		// converted file name.
    		FileInputStream in = new FileInputStream(filePath+"/"+fileName);
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
    		in.close();
    		zos.closeEntry();
    		zos.close();
    		System.out.println("Zip file created.");
    		log.info("Zip file created successfully.");
    		log.info("createZip() complete.");
    	}catch(IOException ex){
    	   log.error("createZip() Exception is : ", ex);
    	}
    	log.info("return created zip file name.");
    	return fName;
    }
}