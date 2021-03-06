package com.ft.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ft.model.util.EmailSender;
import com.ft.model.util.Validator;

@Controller
public class MailController {

	// send mail controllers
	@RequestMapping(value="/email", method=RequestMethod.POST)
	public String sendEmail(HttpSession session,
			@RequestParam("name") String nameFromUser, 
				@RequestParam("email") String emailFromUser, 
					@RequestParam("subject") String subjectFromUser, 
						@RequestParam("message") String messageFromUser) {
		
		boolean validate = Validator.validateString(nameFromUser) && Validator.isValidEmailAddress(emailFromUser) && Validator.validateString(subjectFromUser) && Validator.validateString(messageFromUser);
		
		if(validate){
			try {
				EmailSender.getInstance().contactUs(nameFromUser, emailFromUser, subjectFromUser, messageFromUser);
			} catch (MessagingException e) {
				System.out.println("MailController: " + e.getMessage());
				return "error500";
			} catch (Exception e) {
				System.out.println("MailController -> unexpected error: " + e.getMessage());
				return "error500";
			}
			if (session.getAttribute("logged") == null || !(Boolean)session.getAttribute("logged")) {
				return "redirect: index.html";
			}
		}
		return "redirect: login";
	}
	
	@RequestMapping(value="/login/contact", method=RequestMethod.GET)
	public String sendEmailLogged(HttpSession session) {
		session.setAttribute("url", "contact.jsp");
		return "redirect: ../login";
	}
	
}
