package com.enlightks.utility.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * @author Brijesh
 *
 */
public interface FileNameInterface {
	
	public List<String> getFilesName();
	//public List<String> getFilesName(List<String> list) throws Exception;
	//public List<String> getFilesName(String ftpFilePath, List<String> list) throws Exception;
	public List<String> getAllFTPFiles() throws IOException;

}
