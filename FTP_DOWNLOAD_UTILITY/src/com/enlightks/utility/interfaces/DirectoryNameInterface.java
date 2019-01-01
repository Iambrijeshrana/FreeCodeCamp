package com.enlightks.utility.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * @author Brijesh
 *
 */

public interface DirectoryNameInterface {
	
	public List<String> getDirectoryNames();
	public List<String> getFTPDirectoryNames() throws IOException;
}
