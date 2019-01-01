package com.enlightiks.utilitiy.common;


import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightks.utility.interfaces.DecryptInterface;
import com.enlightks.utility.interfaces.MailServiceInterface;

/**
 * To send a mail.
 * @author Brijesh
 *
 */
public class MailService implements MailServiceInterface{

	private LoadProperties loadProperties;
	private Authenticator auth;
	private static Logger log = null;
	private Properties properties;
	public MailService(){
		log = Logger.getLogger(MailService.class);
		loadProperties = new LoadProperties();
	}
	
	/**
	 * send mail
	 * 
	 * @param to -> provided mail id
	 * @param sub -> set the subject for mail				
	 * @param body -> Message Text
	 * 
	 */
	public void sentMail(String to, String sub, String body) {
		log.info("sentMail() started...");
		try {
			DecryptInterface decryptInterface = new DecryptionUtility();
			// Get Decrypted mail id to send a mail
			final String fromEmail = decryptInterface.decryptedEmail();
			// Get Decrypted password of mail id to send a mail
			final String password = decryptInterface.decryptedEmailPassword();
			// get port number of host
			final String portNumber = loadProperties.getPortNumber();
			// get host address
			final String hostAddress = loadProperties.getHostAddress();
			//Get port Address of host
			final String portAddress = loadProperties.getPortAddress();
			
			properties = new Properties();
			properties.put(hostAddress, "smtp.gmail.com");
			properties.put(portAddress, portNumber);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			// Authenticate the Mail
			auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			// create user session
			Session session = Session.getInstance(properties, auth);
			// creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail));
			InternetAddress[] toAddresses = { new InternetAddress(to) };
			// set recipient mail id
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			// set email subject
			msg.setSubject(sub);
			// set current date 
			msg.setSentDate(new Date());
			// set Message Text
			msg.setContent(body, "text/html");
			// sends the e-mail
			Transport.send(msg);
			log.info("mail sent");
			log.info("sentMail() complete.");
		} catch (Exception e) {
			log.error("sentMail() Exception is : ", e);
		}

	}
}
