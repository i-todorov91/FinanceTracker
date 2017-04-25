package com.ft.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ft.model.DAO.CategoryDAO;
import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.Category;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.user.Holder;
import com.ft.model.user.User;
import com.ft.model.util.exceptions.InvalidCashFlowException;

@Controller
public class UserController {
	
	// login coogin", method=RequestMethod.GET)
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {
		session.removeAttribute("message");
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			return new ModelAndView("main", "userLogin", new Holder());
		}
		session.setAttribute("logged", false);
		return new ModelAndView("login", "userLogin", new Holder());
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("userLogin") Holder holder, HttpSession session, BindingResult result) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			
			return "main";
		}
		
		if(session.getAttribute("logged") == null){
			return "redirect:login";
		}
		
		if(session.isNew()){
			return "redirect:logout";
		}
		
		String email = holder.getEmail();
		String password = holder.getPassword();
		
		try {
			UserDAO userDAO = UserDAO.getInstance();
			if(userDAO.validLogin(email, password)){
				session.setAttribute("logged", true);
				session.setAttribute("username", email);
				session.setMaxInactiveInterval(60);
				String userEmail = (String) session.getAttribute("username");
				User user = userDAO.getAllUsers().get(userEmail);
				session.setAttribute("budgets", user.getBudgets());
				if(!user.getBudgets().isEmpty()){
					session.setAttribute("selectedBudget", user.getBudgets().entrySet().iterator().next().getValue());
				}
				return "main";
			}
			else
			{
				session.setAttribute("logged", false);
				session.setAttribute("message", "There was an error with your E-Mail/Password combination. Please try again.");
				return "login";
			}
		} catch (Exception e) {
			System.out.println("UserController -> getInstance for UserDAO :" + e.getMessage());
			return "error500";
		}
	}
	
	// login -> addbudget controller
	@RequestMapping(value="/login/addbudget", method=RequestMethod.GET)
	public String addbudgetGet(HttpSession session) {
		session.removeAttribute("contact");
		session.removeAttribute("addtransaction");
		session.setAttribute("addbudget", true);
		return "redirect: ../login";
	}
	
	//addbudget controller
	@RequestMapping(value="/login/addbudget", method=RequestMethod.POST)
	public String addBudgetPost(HttpSession session, @RequestParam("name") String name, @RequestParam("amount") Double amount){

		try {
			UserDAO userDAO = UserDAO.getInstance();
			if(session.isNew() || (session.getAttribute("logged") != null && !(Boolean) session.getAttribute("logged"))){
				return "redirect: logout";
			}
			String email = (String) session.getAttribute("username");
			boolean valid = (Boolean) session.getAttribute("logged") != null &&
						(Boolean) session.getAttribute("logged") &&
							session.getAttribute("username") != null &&
								UserDAO.getInstance().getAllUsers().containsKey(email) &&
									amount != null;
			if(valid){
				Budget toAdd = new Budget(name, amount);
				String username = (String) session.getAttribute("username");
				User user = UserDAO.getInstance().getAllUsers().get(username);
				UserDAO.getInstance().addBudget(toAdd, user);
			}
			session.removeAttribute("addbudget");
			return "redirect: ../login";
		} catch (Exception e) {
			System.out.println("UseController -> addBudgetPost: " + e.getMessage());
			return "error500";
		}
	}
	
	// login -> addtransaction controller
	@RequestMapping(value="/login/addtransaction", method=RequestMethod.GET)
	public String addTransactionGet(HttpSession session) {
		String username = (String) session.getAttribute("username");
		long userId = 0;
		try {
			userId = UserDAO.getInstance().getAllUsers().get(username).getId();
		} catch (Exception e){
			System.out.println("UserController->/login/addtransaction GET: " + e.getMessage());
			return "redirect: error500";
		}
		session.setAttribute("addtransaction", true);
		session.removeAttribute("contact");
		session.removeAttribute("addbudget");
		try {
			session.setAttribute("categories", CategoryDAO.getInstance().getAllUserCategories(userId));
		} catch (Exception e) {
			System.out.println("UserController->/login/addtransaction GET: " + e.getMessage());
			return "redirect: error500";
		}
		session.setAttribute("types", Category.TYPE.values());
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/addtransaction", method=RequestMethod.POST)
	public String addTransactionPost(HttpSession session, @RequestParam("type") String type, @RequestParam("quantity") Double quantity, @RequestParam("date") Date date, @RequestParam("category") String categoryName) {
		Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
		Category category = null;
		ArrayList<Category> categories = (ArrayList<Category>) session.getAttribute("categories");
		for(Category i : categories){
			if(i.getName().equals(categoryName)){
				category = i;
				break;
			}
		}
		if(type.equals(Category.TYPE.INCOME)){
			try {
				Income flow = new Income(quantity, date, category);
				UserDAO.getInstance().addIncome(flow, selectedBudget.getId(), (String) session.getAttribute("username"));
			} catch (Exception e) {
				System.out.println("UserController->/login/addtransaction POST: " + e.getMessage());
				return "redirect: error500";
			}
		}
		else{
			try {
				Expense flow = new Expense(quantity, date, category);
				UserDAO.getInstance().addExpense(flow, selectedBudget.getId(), (String) session.getAttribute("username"));
			} catch (Exception e) {
				System.out.println("UserController->/login/addtransaction POST: " + e.getMessage());
				return "redirect: error500";
			}
		}
		return "redirect: ../login";
	}
	
	//logout controller
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("logged", false);
		session.invalidate();
		return "redirect:index.html";
	}
	
	//register controllers
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView registerPage(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.invalidate();
			return new ModelAndView("redirect:/login", "userRegister", new User());
		}
		
		if(session.getAttribute("register") != null){
			session.setAttribute("register", null);
		}
		return new ModelAndView("register", "userRegister", new User());
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("userRegister") User user, BindingResult result, HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.invalidate();
		}
		
		if(result.hasErrors()){
			session.setAttribute("register", "Could not register. Please, enter a valid data!");
			session.setAttribute("color", "alert-danger-register");
		}
		else{
			try {
				if(UserDAO.getInstance().addUser(user)){
					session.setAttribute("register", "Successfully registered. You can <a href=\"login\">login</a> now.");
					session.setAttribute("color", "alert-success-register");
				}
				else{
					session.setAttribute("register", "The user already exists!");
					session.setAttribute("color", "alert-danger-register");
				}
			} catch (Exception e) {
				System.out.println("UseController -> register: " + e.getMessage());
				return "error500";
			}
		}
        return "register";
	}
}
