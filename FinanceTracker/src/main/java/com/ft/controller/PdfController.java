package com.ft.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.User;
import com.ft.model.util.PdfCreator;

@Controller
public class PdfController {

	@RequestMapping(value="/login/pdftester", method=RequestMethod.POST)
	public String postPdf(HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			PdfCreator pc = PdfCreator.getInstance();
			User user = null;
			try {
				String username = (String) session.getAttribute("username");
				user = UserDAO.getInstance().getAllUsers().get(username);
				Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
				List<CashFlow> list = user.getBudgets().get("12").getIncomes();
	//			pc.createCashFlowPdf(user, "Test Cash Flow PDF", list);
				pc.createBudgetPdf(user, selectedBudget.getName(), user.getBudgets().get(selectedBudget.getName()));
	//			pc.createCashFlowPdf(user, "cashflow", list);
			} catch (Exception e) {
				System.out.println("PdfController: " + e.getMessage());
				return "redirect: error500";
			}
		}
		return "redirect: ../login";
	}

	@RequestMapping(value="/login/viewpdf", method=RequestMethod.GET)
	public String viewPdfGet(HttpSession session, @RequestParam("type") String type){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			String username = (String) session.getAttribute("username");
			User user = null;
			PdfCreator pc = PdfCreator.getInstance();
			try{
				if(type.equals("Budget")){
					user = UserDAO.getInstance().getAllUsers().get(username);
					Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
					pc.createBudgetPdf(user, "Budget name: " + selectedBudget.getName(), user.getBudgets().get(selectedBudget.getName()));
					
				}
				else if(type.equals("Account")){
					
				}
				else if(type.equals("Cashflow")){
					
				}
				else{
					
				}
			} catch(Exception e){
				System.out.println("PdfView->GET: " + e.getMessage());
				return "redirect: error500";
			}
		}
		return "viewpdf";
	}
}
