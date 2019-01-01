package com.enlightiks.utilitiy.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightks.utility.interfaces.DecryptInterface;
/** 
 * Decryption of String data; PBE(Password Based Decryption)
 * 
 * @author Brijesh
 */
public final class DecryptionUtility implements DecryptInterface{
	private static Logger log = null;
//  private static Cipher ecipher;
    private static Cipher dcipher;
    // key to decrypte the encrypted text
    private static String key="ezeon8547";
    private LoadProperties loadProperties;
    // 8-byte Salt
    static byte[] salt = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    // Iteration count
    static int iterationCount = 19;
    public DecryptionUtility() { 
    	log = Logger.getLogger(DecryptionUtility.class);
    	loadProperties = new LoadProperties();
    }
     /**     
     * @param secretKey Key used to decrypt data
     * @param encryptedText encrypted text input to decrypt
     * @return Returns plain text after decryption
     */
    public String decrypt(final String secretKey, final String encryptedText)
     throws NoSuchAlgorithmException, 
            InvalidKeySpecException, 
            NoSuchPaddingException, 
            InvalidKeyException,
            InvalidAlgorithmParameterException, 
            UnsupportedEncodingException, 
            IllegalBlockSizeException, 
            BadPaddingException, 
            IOException{
    	log.info("decrypt() started...");
         //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
         // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        //Decryption process; same key will be used for decr
        dcipher=Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
        byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        final String charSet="UTF-8";     
        final String plainStr = new String(utf8, charSet);
        log.info("decrypt() complete and return encrypted text.");
        return plainStr;
    }   

    /**
	 * decryptedPassword
	 * to decrypt the encrypted Password of ftp server
	 * @return decrypted password
	 * @throws FileNotFoundException, Exception
	 */
    public String decryptedPassword() throws FileNotFoundException, Exception{
    	log.info("decryptedPassword() started...");
 		final String ftpPass = loadProperties.getFtpPasswd();
        final String decryptedPassword = decrypt(key, ftpPass);
        log.info("decryptedPassword() complete and return encrypted server password.");
    	return decryptedPassword;
    }
    
    /**
	 * decryptedUsername
	 * to decrypt the encrypted Username of ftp server
	 * @return decrypted username
	 * @throws FileNotFoundException, Exception
	 */
    public String decryptedUsername() throws FileNotFoundException, Exception{
     log.info("decryptedUsername() started...");
     final String ftpUser = loadProperties.getFtpUserName();
     final String decryptedUsername = decrypt(key, ftpUser);
     log.info("decryptedUsername() complete and return encrypted server Username.");
   	 return decryptedUsername;
   }
    
    /**
	 * decryptedServerName
	 * to decrypt the encrypted url of ftp server
	 * @return decrypted url
	 * @throws FileNotFoundException, Exception
	 */
    public String decryptedServerName() throws FileNotFoundException, Exception{
    	log.info("decryptedServerName() started...");
        final String ftpServer = loadProperties.getFtpServerName();
        final String ftpServerName = decrypt(key, ftpServer);
        log.info("decryptedServerName() complete and return encrypted server Name.");
      	return ftpServerName;
      }
   /* public static void main(String[] args) throws Exception {
		CryptoUtil c = new CryptoUtil();
		System.out.println(c.decryptedServerName());
	}*/
    /**
	 * decryptedEmail
	 * to decrypt the encrypted Mail Id to send the mail
	 * @return decrypted email id
	 * @throws FileNotFoundException, Exception
	 */
    public String decryptedEmail() throws FileNotFoundException, Exception{
    	log.info("decryptedEmail() started...");
        final String fromEmail = loadProperties.getFromEmail();
        final String demail = decrypt(key, fromEmail);
        log.info("decryptedEmail() complete and return encrypted e-mail id.");
      	return demail;
      }
    
    /**
	 * decryptedEmailPassword
	 * to decrypt the encrypted Mail Id password to send the mail
	 * @return decrypted email id password
	 * @throws FileNotFoundException, Exception
	 */
    public String decryptedEmailPassword() throws FileNotFoundException, Exception{
    	log.info("decryptedEmailPassword() started...");
        final String password = loadProperties.getPassword();
        final String dpwd = decrypt(key, password);
        log.info("decryptedEmailPassword() complete and return encrypted e-mail id password.");
      	return dpwd;
      }
    
   }