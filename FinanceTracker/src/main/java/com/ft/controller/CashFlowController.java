package com.ft.controller;

import java.sql.SQLException;
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
import org.springframework.web.servlet.ModelAndView;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.Holder;
import com.ft.model.user.User;
import com.ft.model.util.exceptions.InvalidCashFlowException;

@Controller
public class CashFlowController {
	
	@RequestMapping(value="/cashflow", method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, TreeSet<CashFlow>> getCashFlow(HttpSession session,
			@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
					@RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) {
		
		if (fromDate.after(toDate)) {
			Date temp = fromDate;
			fromDate = toDate;
			toDate = temp;
		}
		
		HashMap<String, TreeSet<CashFlow>> cashFlows = new HashMap();
		String username = (String) session.getAttribute("username");
		User user = null;
		try {
			user = UserDAO.getInstance().getAllUsers().get(username);
		} catch (Exception e) {
			System.out.println("CashFlowController -> getAllUsers: " + e.getMessage());
			return null;
		}
		Budget budget = user.getBudgets().get(session.getAttribute("budget"));
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			//add expenses
			for (CashFlow cf : budget.getExpenses()) {
				String type = cf.getType().toString();
				if (cf.getDate().before(fromDate) || cf.getDate().after(toDate)) {
					continue;
				}
				if (!cashFlows.containsKey(type)) {
					cashFlows.put(type, new TreeSet<>());
				}
				cashFlows.get(type).add(cf);
			}
			//add income
			for (CashFlow cf : budget.getIncomes()) {
				String type = cf.getType().toString();
				if (cf.getDate().before(fromDate) || cf.getDate().after(toDate)) {
					continue;
				}
				if (!cashFlows.containsKey(type)) {
					cashFlows.put(type, new TreeSet<>());
				}
				cashFlows.get(type).add(cf);
			}
		}
		return cashFlows;
	}
	
}
