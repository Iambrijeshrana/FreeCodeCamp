package com.pth.email;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.ComparisonTerm;

public class EmailReader {
    private String saveDirectory;

    /**
     * Sets the directory where attached files will be stored.
     * 
     * @param dir
     *            absolute path of the directory
     */
    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }

    /**
     * Downloads new messages and saves attachments to disk if any.
     * 
     * @param host
     * @param port
     * @param userName
     * @param password
     * @throws IOException
     */
    public void downloadEmailAttachments(String host, String port,
            String userName, String password, Date startDate, Date endDate) {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", userName, password);
            // ...
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            SearchTerm olderThan = new ReceivedDateTerm (ComparisonTerm.LT, startDate);
            SearchTerm newerThan = new ReceivedDateTerm (ComparisonTerm.GT, endDate);
            SearchTerm andTerm = new AndTerm(olderThan, newerThan);
            //Message[] arrayMessages = inbox.getMessages(); <--get all messages
            Message[] arrayMessages = inbox.search(andTerm);
            for (int i = arrayMessages.length; i > 0; i--) { //from newer to older
                Message msg = arrayMessages[i-1];
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String sentDate = msg.getSentDate().toString();
                String receivedDate = msg.getReceivedDate().toString();

                String contentType = msg.getContentType();
                String messageContent = "";

                // store attachment file name, separated by comma
                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) msg.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart
                                .getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part
                                .getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            attachFiles += fileName + ", ";
                            part.saveFile(saveDirectory + File.separator + fileName);
                        } else {
                            // this part may be the message content
                            messageContent = part.getContent().toString();
                        }
                    }
                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0,
                                attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object content = msg.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }

                // print out details of each message
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Received: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);
            }

            // disconnect
            inbox.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Runs this program with Gmail POP3 server
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
    	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   System.out.println(dtf.format(now));  
    	
        String host = "pop.gmail.com";
        String port = "995";
        String userName = "email@gmail.com";
        String password = "Password";
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-10-30");
        String c = dateFormat.format(date);
        Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(c);  
//        Date endDate = Date.parse(dtf.format(now));//new SimpleDateFormat("yyyy-MM-dd").parse("2018-09-17");
        String saveDirectory = "D:/Attachment/Download";

        EmailReader receiver = new EmailReader();
        receiver.setSaveDirectory(saveDirectory);
        receiver.downloadEmailAttachments(host, port, userName, password,startDate,endDate);

    }
}
