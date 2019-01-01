package com.enlightiks.utilitiy.properties;

import java.io.FileNotFoundException;
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


/**
 * Encryption of String data; PBE(Password Based Encryption and
 * Decryption)
 * 
 * @author Brijesh
 */

public class EncryptionUtil {
	private Cipher ecipher;
	private String key = "ezeon8547";
	private String ftpServer;
	private String ftpUser;
	private String ftpPass;
	private static Logger log = null;
	// 8-byte Salt
	private byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
	// Iteration count
	private int iterationCount = 19;

	public EncryptionUtil() {
		log = Logger.getLogger(EncryptionUtil.class);
	}

	/**
	 * 
	 * @param ftpServerName
	 *            enter by user on ui
	 * @param ftpUserName
	 *            enter by user on ui
	 * @param ftpPasswd
	 * 			  enter by user on ui
	 * @return 
	 * 
	 */
	public void getEncryptedValues(String ftpServerName, String ftpUserName,
			String ftpPasswd) {
		log.info("getEncryptedValues() started...");
		try {
			ftpPass = ftpPasswd;
			ftpUser = ftpUserName;
			ftpServer = ftpServerName;
			encryptedPassword();
			encryptedServerName();
			encryptedUsername();
			log.info("getEncryptedValues() complete.");
		} catch (FileNotFoundException e) {
			log.error("getEncryptedValues() Exception is : ", e);
		} catch (Exception e) {
			log.error("getEncryptedValues() Exception is : ", e);
		}
	}

	/**
	 * 
	 * @param secretKey
	 *            Key used to encrypt data
	 * @param plainText
	 *            Text input to be encrypted
	 * @return Returns encrypted text
	 * 
	 */
	public String encrypt(String secretKey, String plainText)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		log.info("encrypt() started...");
		// Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt,
				iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
				.generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
				iterationCount);

		// Enc process
		ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		String charSet = "UTF-8";
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		String encStr = new sun.misc.BASE64Encoder().encode(out);
		log.info("encrypt() complete.");
		log.info("return encrypted text");
		return encStr;
	}

	/**
	 * To Encrypt the ftp server password
	 * @return encrypted Password
	 * @throws FileNotFoundException, Exception
	 */
	public String encryptedPassword() throws FileNotFoundException, Exception {
		log.info("encryptedPassword() started...");
		String encftpPass = encrypt(key, ftpPass);
		log.info("encryptedPassword() complete.");
		log.info("return encrypted ftp password.");
		return encftpPass;
	}

	/**
	 * To Encrypt the ftp server username
	 * @return encrypted UserName
	 * @throws FileNotFoundException, Exception
	 */
	public String encryptedUsername() throws FileNotFoundException, Exception {
		log.info("encryptedUsername() started...");
		String encftpUser = encrypt(key, ftpUser);
		log.info("encryptedUsername() complete.");
		log.info("return encrypted ftp user name.");
		return encftpUser;
	}

	/**
	 * To Encrypt the ftp server name
	 * @return encrypted ftp server name
	 * @throws FileNotFoundException, Exception
	 */
	public String encryptedServerName() throws FileNotFoundException, Exception {
		log.info("encryptedServerName() started...");
		String encftpServer = encrypt(key, ftpServer);
		log.info("encryptedServerName() complete.");
		log.info("return encrypted server name");
		return encftpServer;
	}

}