package com.panchuk.taxapp.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    static final Logger logger = Logger.getLogger(EmailSender.class);

    public static void sendMessage(String error) throws Exception{

        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");

        properties.put("mail.smtp.starttls.enable","true");

        properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port

        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class

        properties.put("mail.smtp.host","smtp.ukr.net");

        properties.put("mail.smtp.port","465");

        properties.put("mail.smtp.ssl.Enable","true");

        properties.put("mail.smtp.user","alexanderpanchuk@ukr.net");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alexanderpanchuk@ukr.net", "tyTqHRNlIf1SJU4C");
            }
        };

        Session session = Session.getDefaultInstance(properties,auth);

        System.out.println("Session created");
        logger.info("Session created");

        MimeMessage message = new MimeMessage(session);

        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");

        message.setFrom(new InternetAddress("alexanderpanchuk@ukr.net"));

        message.setReplyTo(InternetAddress.parse("alexanderpanchuk@ukr.net", false));

        message.setSubject("The critical error occurred","UTF-8");

        message.setText(error,"UTF-8");

        message.setRecipient(Message.RecipientType.TO,new InternetAddress("alexanderpanchuk@ukr.net"));


        Transport.send(message);

        System.out.println("Message was sent successfully");

        logger.info("Message was sent successfully");
    }

    public static void main(String[] args) {
        logger.error("error test");
        try {
            sendMessage("error send");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
