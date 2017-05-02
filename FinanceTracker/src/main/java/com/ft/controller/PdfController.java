package com.ft.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.User;
import com.ft.model.util.PdfCreator;

@Controller
public class PdfController {

	@RequestMapping(value="/login/createpdf", method=RequestMethod.GET)
	public String createPdfGet(HttpSession session, @RequestParam("type") String type, Model model){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			try{
				String username = (String) session.getAttribute("username");
				User user = UserDAO.getInstance().getAllUsers().get(username);;
				PdfCreator pc = PdfCreator.getInstance();
				File file = null;
				
				if(type.equals("Budget")){
					Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
					if(selectedBudget.getIncomes().isEmpty() && selectedBudget.getExpenses().isEmpty()){
						return "redirect: ../login";
					}
					file = pc.createBudgetPdf(user, "Budget name: " + selectedBudget.getName(), user.getBudgets().get(selectedBudget.getName()));
				}
				else if(type.equals("Account")){
					file = pc.CreateAccountInfoPdf(user);
				}
				else if(type.equals("Cashflow")){
					ArrayList<CashFlow> cashFlow = (ArrayList<CashFlow>) session.getAttribute("filteredData");
					file = pc.createCashFlowPdf(user, "Cashflow filtered data", cashFlow);
				} 
				session.setAttribute("filename", file.getName());
				session.setAttribute("from", "createpdf");
				return "redirect: viewpdf";
			} catch(Exception e){
				System.out.println("PdfView->GET: " + e.getMessage());
				e.printStackTrace();
				return "redirect: error500";
			}
		}
		return "redirect: ../login";
	}
	
	@RequestMapping(value="/login/viewpdf", method=RequestMethod.GET)
	@ResponseBody
	public void viewPdfGet(HttpSession session, HttpServletResponse res){
		
		try {
			File file = new File(PdfCreator.DESTINATION + (String) session.getAttribute("filename") + ".pdf");
			if(!file.exists() || session.getAttribute("from") == null){
				res.sendRedirect("../login");
				return;
			}
			Files.copy(file.toPath(), res.getOutputStream());
		} catch (IOException e) {
			System.out.println("View pdf: " + e.getMessage());
			try {
				session.removeAttribute("from");
				res.sendRedirect("../login");
			} catch (IOException e1) {
				System.out.println("view pdf Get: " + e1.getMessage());
			}
		}
		session.removeAttribute("from");
	}
	
	@RequestMapping(value="/login/accountinformation", method=RequestMethod.GET)
	public String viewAccountInformationGet(HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "accountinformation.jsp");
			
		} 
		return "redirect: ../login";
	}
}
