package com.ft.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ft.model.DAO.EmailSender;

@Controller
public class MailController {

	// send mail controllers
	@RequestMapping(value="/email", method=RequestMethod.POST)
	public String sendEmail(HttpSession session,
			@RequestParam("name") String nameFromUser, 
				@RequestParam("email") String emailFromUser, 
					@RequestParam("subject") String subjectFromUser, 
						@RequestParam("message") String messageFromUser) {
		
		try {
			EmailSender.getInstance().contactUs(nameFromUser, emailFromUser, subjectFromUser, messageFromUser);
		} catch (MessagingException e) {
			System.out.println("MailController: " + e.getMessage());
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			System.out.println("MailController -> unexpected error: " + e.getMessage());
			return "error";
		}
		if (session.getAttribute("logged") == null || !(Boolean)session.getAttribute("logged")) {
			return "redirect: index.html";
		}
		return "redirect: login";
	}
	
	@RequestMapping(value="/login/contact", method=RequestMethod.GET)
	public String sendEmailLogged(HttpSession session) {
		session.setAttribute("url", "contact.jsp");
		return "redirect: ../login";
	}
	
}
