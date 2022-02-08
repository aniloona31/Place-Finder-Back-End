package com.PlaceFinder.CollegeProject.Service;

import javax.mail.Authenticator;
import javax.mail.Message;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MailService {
	
	@Async 
	public void sendMail(NotificationMail notificationMail) throws Exception {
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		
		Session session = Session.getInstance(properties,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("placefinder22@gmail.com","");
			}
		});
		session.setDebug(true);
		
		MimeMessage mail = new MimeMessage(session);
		try {
			mail.setFrom("placefinder22@gmail.com");
			mail.addRecipient(Message.RecipientType.TO,new InternetAddress(notificationMail.getRecipent()));
			mail.setSubject(notificationMail.getSubject());
			mail.setText(notificationMail.getBody());
		
			Transport.send(mail);
		}catch(Exception e){
			throw new Exception(e);
		}
			
	}
}
