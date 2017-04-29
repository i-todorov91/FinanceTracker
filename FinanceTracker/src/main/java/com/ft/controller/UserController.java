package com.ft.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ft.model.DAO.CategoryDAO;
import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.budget.flows.Category;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.user.Holder;
import com.ft.model.user.User;
import com.ft.model.util.Validator;

@Controller
public class UserController {
	
	// login controller
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {
		session.removeAttribute("message");
		try{
			if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
				String username = (String) session.getAttribute("username");
				if(session.getAttribute("selectedBudget") == null){
					User user = null;
					try {
						user = UserDAO.getInstance().getAllUsers().get(username);
					} catch (Exception e){
						System.out.println("UserController->/login GET-> load user budgets: " + e.getMessage());
					}
					session.setAttribute("budgets", user.getBudgets());
					if(!user.getBudgets().isEmpty()){
						session.setAttribute("selectedBudget", user.getBudgets().entrySet().iterator().next().getValue());
					}
				}
				return new ModelAndView("main", "userLogin", new Holder());
			}
			session.setAttribute("logged", false);
			return new ModelAndView("login", "userLogin", new Holder());
		} catch(Exception e){
			return new ModelAndView("error500");
		}
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

				User user = UserDAO.getInstance().getAllUsers().get(email);
				session.setMaxInactiveInterval(60*30); // 30 minutes session
				session.setAttribute("logged", true);
				session.setAttribute("username", email);
				session.setAttribute("budgets", user.getBudgets());
				if(!user.getBudgets().isEmpty()){
					session.setAttribute("selectedBudget", user.getBudgets().entrySet().iterator().next().getValue());
				}
				session.setAttribute("url", "diagrams.jsp");
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
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "budget.jsp");
		}
		return "redirect: ../login";
	}
	
	//addbudget controller
	@RequestMapping(value="/login/addbudget", method=RequestMethod.POST)
	public String addBudgetPost(HttpSession session, @RequestParam("name") String name, @RequestParam("amount") Double amount){

		try {
			if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){

				UserDAO userDAO = UserDAO.getInstance();
				String username = (String) session.getAttribute("username");
				boolean valid = userDAO.getAllUsers().containsKey(username) &&
										amount != null && Validator.validBalance(amount) && name.trim().length() >= 2 && name.trim().length() <= 15;
				if(valid){
					Budget toAdd = new Budget(name.trim(), amount); 
					User user = UserDAO.getInstance().getAllUsers().get(username);
					UserDAO.getInstance().addBudget(toAdd, user);
				}
			}
			return "redirect: ../login";
		} catch (Exception e) {
			System.out.println("UseController -> addBudgetPost: " + e.getMessage());
			return "error500";
		}
	}
	
	// login -> addtransaction controller
	@RequestMapping(value="/login/addtransaction", method=RequestMethod.GET)
	public String addTransactionGet(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			String username = (String) session.getAttribute("username");
			long userId = 0;
			try {
				userId = UserDAO.getInstance().getAllUsers().get(username).getId();
			} catch (Exception e){
				System.out.println("UserController->/login/addtransaction GET: " + e.getMessage());
				return "redirect: error500";
			}
			try {
				session.setAttribute("categories", CategoryDAO.getInstance().getAllDefaultList());
				session.setAttribute("incomeCategories", CategoryDAO.getInstance().getAllUserIncomeCategories(userId));
				session.setAttribute("expenseCategories", CategoryDAO.getInstance().getAllUserExpenseCategories(userId));
			} catch (Exception e) {
				System.out.println("UserController->/login/addtransaction GET: " + e.getMessage());
				return "redirect: error500";
			}
			session.setAttribute("url", "transaction.jsp");
			session.setAttribute("selectedType", Category.TYPE.INCOME.toString());
			session.setAttribute("types", Category.TYPE.values());
		}
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/addtransaction", method=RequestMethod.POST)
	public String addTransactionPost(HttpSession session, @RequestParam("quantity") Double quantity, @RequestParam("date") String date, @RequestParam("category") String categoryName, @RequestParam("description") String description) {
		
		boolean valid = session.getAttribute("selectedBudget") != null && quantity != null && Validator.validBalance(quantity); 
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && valid){
			Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
			Category category = null;
			ArrayList<Category> categories = (ArrayList<Category>) session.getAttribute("categories");
			for(Category i : categories){
				if(i.getName().equals(categoryName)){
					category = i;
					break;
				}
			}

			String type = (String) session.getAttribute("selectedType");
			if(category == null){
				if(type.equals(Category.TYPE.INCOME.toString())){
					ArrayList<Category> cats = (ArrayList<Category>) session.getAttribute("incomeCategories");
					for(Category i : cats){
						if(i.getName().equals(categoryName)){
							category = i;
							break;
						}
					}
				}
				else if(type.equals(Category.TYPE.EXPENSE.toString())){
					ArrayList<Category> cats = (ArrayList<Category>) session.getAttribute("expenseCategories");
					for(Category i : cats){
						if(i.getName().equals(categoryName)){
							category = i;
							break;
						}
					}
				}
			}
			
			if(category != null && true){
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date date1 = new Date();
				try {
					date1 = formatter.parse(date);
				} catch (ParseException e1) {
					System.out.println("UserController-> addTransaction -> parseDate: " + e1.getMessage());
					return "redirect: ../login";
				}
				if(type.equals(Category.TYPE.INCOME.toString())){
					try {
						Income flow = new Income(quantity, date1, category, description);
						UserDAO.getInstance().addIncome(flow, selectedBudget.getId(), (String) session.getAttribute("username"));
					} catch (Exception e) {
						System.out.println("UserController->/login/addtransaction POST: " + e.getMessage());
						e.getStackTrace();
						return "redirect: error500";
					} 
				}
				else if(type.equals(Category.TYPE.EXPENSE.toString())){
					try {
						Expense flow = new Expense(quantity, date1, category, description);
						UserDAO.getInstance().addExpense(flow, selectedBudget.getId(), (String) session.getAttribute("username"));
					} catch (Exception e) {
						System.out.println("UserController->/login/addtransaction POST: " + e.getMessage());
						return "redirect: error500";
					}
				}
			}
		}
		return "redirect: ../login";
	}
	
	//changebudget controller
	@RequestMapping(value="/login/changebudget", method=RequestMethod.POST)
	public String changebudget(HttpSession session, @RequestParam("clicked") String clicked) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			HashMap<String, Budget> budgets = (HashMap<String, Budget>) session.getAttribute("budgets");
			for(Entry<String, Budget> i : budgets.entrySet()){
				if(i.getValue().getName().equals(clicked)){
					session.setAttribute("selectedBudget", i.getValue());
				}
			}
		}
		return "redirect: ../login";		
	}
	
	//change type controller
	@RequestMapping(value="/login/changetype", method=RequestMethod.POST)
	public String changetype(HttpSession session, @RequestParam("type") String clicked) {
		
		session.setAttribute("selectedType", clicked);
		return "redirect: ../login";		
	}
	
	//removebudget controller
	@RequestMapping(value="/login/removebudget", method=RequestMethod.GET)
	public String removeBudgetGet(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "removebudget.jsp");
		}
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/removebudget", method=RequestMethod.POST)
	public String removeBudgetPost(HttpSession session, @RequestParam("budgetName") String budgetName) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			try {
				String username = (String) session.getAttribute("username");
				User user = UserDAO.getInstance().getAllUsers().get(username);
				
				// validation for valid budgetName is in this method
				UserDAO.getInstance().removeBudget(username, budgetName); 
				session.setAttribute("budgets", user.getBudgets()); 
				Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
				if(!user.getBudgets().isEmpty() && !user.getBudgets().containsKey(selectedBudget.getName())){
					session.setAttribute("selectedBudget", user.getBudgets().entrySet().iterator().next().getValue());
				}
				if(user.getBudgets().isEmpty()){
					session.setAttribute("selectedBudget", null);
				}
			} catch (Exception e){
				System.out.println("login/removebudget POST: " + e.getMessage());
				return "redirect: ../error500";
			}
			
		}
		return "redirect: ../login";
	} 
	
	//filterdate controller
	@RequestMapping(value="/login/filterdate", method=RequestMethod.GET)
	public String filterDateGet(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "filterdate.jsp");
		}
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/filterdate", method=RequestMethod.POST)
	public String filterDatePost(HttpSession session, @RequestParam("from") String from, @RequestParam("to") String to, Model model) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			
			session.removeAttribute("filteredData");
			// validate from and to data
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date fromDate = new Date();
			Date toDate = new Date();
			boolean sameDate = false;
			try{
				fromDate = formatter.parse(from);
				toDate = formatter.parse(to);
				sameDate = (fromDate.getDay() == toDate.getDay()) && (fromDate.getMonth() == toDate.getMonth()) && (fromDate.getYear() == toDate.getYear());
				if(fromDate.after(toDate) && !sameDate){ 
					throw new ParseException(to, 0);
				}
			} catch(ParseException e){
				System.out.println("UserController -> login/filterdate -> data parse: " + e.getMessage());
				return "redirect: ../login";
			}
			
			// get the selected budget and check if it is valid
			Budget budget = (Budget) session.getAttribute("selectedBudget");
			String username = (String) session.getAttribute("username");
			try{
				if(budget != null && UserDAO.getInstance().getAllUsers().get(username).getBudgets().containsKey(budget.getName())){
					ArrayList<CashFlow> result = new ArrayList<>();
					
					// check all incomes
					for(CashFlow i : budget.getIncomes()){
						if(sameDate && i.getDate().getDay() == fromDate.getDay() && i.getDate().getMonth() == fromDate.getMonth() && i.getDate().getYear() == fromDate.getYear()){
							result.add(i);
							continue;
						}
						if(i.getDate().after(fromDate) && i.getDate().before(toDate)){
							result.add(i);
						}
					}
					
					// check all expenses
					for(CashFlow i : budget.getExpenses()){
						if(sameDate && i.getDate().getDay() == fromDate.getDay() && i.getDate().getMonth() == fromDate.getMonth() && i.getDate().getYear() == fromDate.getYear()){
							result.add(i);
							continue;
						}
						if(i.getDate().after(fromDate) && i.getDate().before(toDate)){
							result.add(i);;
						}
					}
					model.addAttribute("filteredData", result);
					System.out.println(model);
					System.out.println(result);
					//session.setAttribute("filteredData",result);
				}
			} catch(Exception e){
				return "redirect: ../login";
			}
		}
		return "redirect: ../login";
	}
	
	//viewdiagrams controller
	@RequestMapping(value="/login/viewdiagrams", method=RequestMethod.GET)
	public String viewDiagrams(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "diagrams.jsp");
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
	public String register(@ModelAttribute("userRegister") @Valid  User user, BindingResult result, HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.invalidate();
		}
		
		if(!result.hasErrors()){
			try {
				if(UserDAO.getInstance().addUser(user)){
					session.setAttribute("register", true);
				}
				else{
					session.setAttribute("register", false);
				}
			} catch (Exception e) {
				System.out.println("UseController -> register: " + e.getMessage());
				return "error500";
			}
		}
        return "register";
	}
}
