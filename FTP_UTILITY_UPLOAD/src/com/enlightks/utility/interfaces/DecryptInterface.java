package com.enlightks.utility.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Brijesh
 *
 */

public interface DecryptInterface {

	 public String decrypt(final String secretKey, final String encryptedText)throws NoSuchAlgorithmException, InvalidKeySpecException, 
		            NoSuchPaddingException, 
		            InvalidKeyException,
		            InvalidAlgorithmParameterException, 
		            UnsupportedEncodingException, 
		            IllegalBlockSizeException, 
		            BadPaddingException, 
		            IOException;
	 
	 public String decryptedPassword() throws FileNotFoundException, Exception;
	 public String decryptedUsername() throws FileNotFoundException, Exception;
	 public String decryptedServerName() throws FileNotFoundException, Exception;
	 public String decryptedEmail() throws FileNotFoundException, Exception;
	 public String decryptedEmailPassword() throws FileNotFoundException, Exception; 
	
}
