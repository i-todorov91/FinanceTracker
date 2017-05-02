package com.ft.controller;

import java.util.ArrayList;

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

	@RequestMapping(value="/login/viewpdf", method=RequestMethod.GET)
	public String viewPdfGet(HttpSession session, @RequestParam("type") String type){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			try{
				String username = (String) session.getAttribute("username");
				User user = UserDAO.getInstance().getAllUsers().get(username);;
				PdfCreator pc = PdfCreator.getInstance();
				
				if(type.equals("Budget")){
					Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
					pc.createBudgetPdf(user, "Budget name: " + selectedBudget.getName(), user.getBudgets().get(selectedBudget.getName()));
					return "redirect: ../pdfs/" + PdfCreator.generateFileName(user) + ".pdf";
				}
				else if(type.equals("Account")){
					pc.CreateAccountInfoPdf(user);
					return "redirect: ../pdfs/" + PdfCreator.generateFileName(user) + ".pdf";
				}
				else if(type.equals("Cashflow")){
					ArrayList<CashFlow> cashFlow = (ArrayList<CashFlow>) session.getAttribute("filteredData");
					pc.createCashFlowPdf(user, "Cashflow filtered data", cashFlow);
					return "redirect: ../pdfs/" + PdfCreator.generateFileName(user) + ".pdf";
				} 
				else{
					
				}
			} catch(Exception e){
				System.out.println("PdfView->GET: " + e.getMessage());
				return "redirect: error500";
			}
		} 
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/accountinformation", method=RequestMethod.GET)
	public String viewAccountInformationGet(HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "accountinformation.jsp");
			
		} 
		return "redirect: ../login";
	}
}
