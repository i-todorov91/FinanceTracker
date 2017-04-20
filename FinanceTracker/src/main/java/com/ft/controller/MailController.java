package com.ft.controller;

import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ft.model.DAO.EmailSender;

@Controller
public class MailController {

	// send mail controllers
	@RequestMapping(value="/mail", method=RequestMethod.GET)
	public String sendEmail() {
		try {
			EmailSender.getInstance().notify("zpetrov96@gmail.com");
		} catch (MessagingException e) {
			System.out.println("MailController: " + e.getMessage());
			e.printStackTrace();
		}
		return "main";
	}
	
}
