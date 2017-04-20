package com.ft.model.DAO;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	private Transport transport;
	
	private static EmailSender instance = null;
	
	public synchronized static EmailSender getInstance(){
		if(instance == null){
			instance = new EmailSender();
		}
		return instance;
	}

    public synchronized void notify(String email) throws MessagingException{
        try{
        	
            String host ="smtp.gmail.com" ;
            String sender = "financetrackeritt@gmail.com";
            String pass = "financeTrackerITT1";
            String from = "financetrackeritt@gmail.com";
            String link = "<a href=\"http://localhost:8080/FinanceTracker/\">Finance Tracker</a>";
            String subject = "Notification from FinanceTracker!";
            String messageHtml = "Hello,<br><br>We've noticed you haven't been using our amazing site " + link + "!<br>Come back we miss you ;(<br><br>Sincerely,<br><strong>Finance Tracker</strong> team. ";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(email)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setContent(messageHtml, "text/html; charset=utf-8");

           transport = mailSession.getTransport("smtp");
           transport.connect(host, sender, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           System.out.println("message send successfully");
        }catch(Exception ex){
            System.out.println("EmailSender : " + ex.getMessage());
            throw ex;
        }finally {
            try {
				transport.close();
			} catch (MessagingException e) {
				System.out.println("EmailSender -> transport.close() : " + e.getMessage());
				throw e;
			}
		}

    }
    
}