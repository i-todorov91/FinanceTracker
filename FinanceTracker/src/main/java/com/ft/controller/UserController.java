package com.ft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.user.Holder;
import com.ft.model.user.User;
import com.ft.model.util.Validator;

@Controller
public class UserController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login", "userLogin", new Holder());
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("userLogin") Holder holder, HttpSession session, BindingResult result) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			return "main";
		}
		String email = holder.getEmail();
		String password = holder.getPassword();
		if(UserDAO.getInstance().validLogin(email, password)){
			session.setAttribute("logged", true);
			session.setAttribute("username", email);
			session.setMaxInactiveInterval(60);
			return "main";
		}
		else
		{
			session.setAttribute("logged", false);
			return "login";
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
		if(valid){
			String name = request.getParameter("name");
			double balance1 = Double.parseDouble((String) balance);
			Budget toAdd = new Budget(name, balance1);
			String username = (String) session.getAttribute("username");
			User user = UserDAO.getInstance().getAllUsers().get(username);
			UserDAO.getInstance().addBudget(toAdd, user);
		}
		return "redirect:main";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView registerPage(HttpSession session) {
		
		if(session.getAttribute("register") != null){
			session.setAttribute("register", null);
		}
		return new ModelAndView("register", "userRegister", new User());
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("userRegister") User user, BindingResult result, HttpSession session){
		
		session.setAttribute("register", null);
		if(result.hasErrors()){
			session.setAttribute("register", "Could not register. Please, enter a valid data!");
			session.setAttribute("color", "alert-danger-register");
		}
		else{
			if(UserDAO.getInstance().addUser(user)){
				session.setAttribute("register", "Successfully registered. You can <a href=\"login\">login</a> now.");
				session.setAttribute("color", "alert-success-register");
			}
			else{
				session.setAttribute("register", "The user already exists!");
				session.setAttribute("color", "alert-danger-register");
			}
		}
        return "register";
	}
}
