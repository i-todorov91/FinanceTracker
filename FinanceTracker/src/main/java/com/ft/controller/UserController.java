package com.ft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.user.User;
import com.ft.model.util.Validator;

@Controller
public class UserController {
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Model model, HttpSession session, HttpServletRequest request) {

		if((session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != null && session.getAttribute("IP") != request.getRemoteAddr())){
			return "redirect:index";
		}
		else{
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if(Validator.isValidEmailAddress(email) && Validator.validPassword(password)){
				if(UserDAO.getInstance().validLogin(email, password)){
					session.setAttribute("logged", true);
					session.setAttribute("username", email);
					session.setAttribute("IP", request.getRemoteAddr());
					session.setMaxInactiveInterval(60);
					return "redirect:main";
				}
				else
				{
					session.setAttribute("logged", false);
					model.addAttribute("login", false);
					return "redirect:login";
				}
			}
			else{
				session.setAttribute("logged", false);
				model.addAttribute("login", false);
				return "redirect:login";
			}
		}
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index";
	}
	
	@RequestMapping(value="/addbudget", method=RequestMethod.POST)
	public String addBudget(HttpSession session, HttpServletRequest request){
		
		if(session.isNew() || (session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != request.getRemoteAddr())){
			return "redirect:/logout";
		}
		Object balance = request.getParameter("balance");
		String email = (String) session.getAttribute("username");
		boolean valid = (Boolean) session.getAttribute("logged") != null &&
					(Boolean) session.getAttribute("logged") &&
						session.getAttribute("username") != null &&
							UserDAO.getInstance().getAllUsers().containsKey(email) &&
								balance != null &&
									Validator.isValidNumber(balance);
		if(valid) {
			String name = request.getParameter("name");
			double balance1 = Double.parseDouble((String) balance);
			Budget toAdd = new Budget(name, balance1);
			String username = (String) session.getAttribute("username");
			User user = UserDAO.getInstance().getAllUsers().get(username);
			UserDAO.getInstance().addBudget(toAdd, user);
		}
		return "redirect:main";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(Model model, HttpSession session, HttpServletRequest request){
		
		// if already logged invalidate the session
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != request.getRemoteAddr()){
			session.invalidate();
			return "redirect:index";
		}
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String firstName = request.getParameter("firstName");
		String secondName = request.getParameter("secondName");
		
		// check if all the input is correct
		boolean isValid = Validator.validateString(email) && 
								Validator.validateString(password) &&
										Validator.validateString(confirmPassword) &&
												Validator.validateString(firstName) &&
														Validator.validateString(secondName) &&
																Validator.isValidEmailAddress(email) &&
																		Validator.validPassword(password) &&
																				Validator.validPassword(confirmPassword) &&
																						password.equals(confirmPassword);
		
		// if it is not correct add invalid property
		if(!isValid){
			session.setAttribute("register", "invalid");
		}
		else if(UserDAO.getInstance().addUser(email, password, firstName, secondName)){
			session.setAttribute("register", true);
		}
		else{
			session.setAttribute("register", false);
		}
		System.out.println("IM here");
		return "redirect:register";
	}
	
	@RequestMapping(value="/loginpage", method=RequestMethod.GET)
	public String loginPage() {
		
		return "login";
	}
	
	@RequestMapping(value="/registerpage", method=RequestMethod.GET)
	public String registerPage() {
		
		return "register";
	}
}
