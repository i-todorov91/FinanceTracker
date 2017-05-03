package com.ft.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ft.model.DAO.CategoryDAO;
import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.budget.flows.Category;
import com.ft.model.user.User;
import com.ft.model.util.Validator;

@Controller
public class CashFlowController {
	
	@RequestMapping(value="/login/addcategory", method=RequestMethod.GET)
	public String addcategoryGet(HttpSession session) {
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "category.jsp");
		}
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/addcategory", method=RequestMethod.POST)
	public String addcategoryPost(HttpSession session, @RequestParam("type") String type, @RequestParam("name") String name) {
		
		boolean valid = (type.equals(Category.TYPE.INCOME.toString()) || type.equals(Category.TYPE.EXPENSE.toString())) && Validator.validateString(name);
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && valid){
			Category.TYPE typ = null;
			if(type.equals(Category.TYPE.INCOME.toString())){
				typ = Category.TYPE.INCOME;
			} else if(type.equals(Category.TYPE.EXPENSE.toString())){
				typ = Category.TYPE.EXPENSE;
			}
			Category toAdd = null;
			try {
				toAdd = new Category(name, "aaa", typ);
				long userID = UserDAO.getInstance().getAllUsers().get(session.getAttribute("username")).getId();
				CategoryDAO.getInstance().addCustomCategory(userID, toAdd);
			} catch (Exception e) {
				System.out.println("CashFlowController-> addCategoryPost: " + e.getMessage());
				return "redirect: ../login";
			}
			session.setAttribute("url", "transaction.jsp");
			if(type.equals(Category.TYPE.INCOME.toString())){
				ArrayList<Category> newCats = (ArrayList<Category>) session.getAttribute("incomeCategories");
				newCats.add(toAdd);
				session.setAttribute("incomeCategories", newCats);
			} else if(type.equals(Category.TYPE.EXPENSE.toString())){
				ArrayList<Category> newCats = (ArrayList<Category>) session.getAttribute("expenseCategories");
				newCats.add(toAdd);
				session.setAttribute("expenseCategories", newCats);
			}
		}
		return "redirect: ../login";
	}

}
