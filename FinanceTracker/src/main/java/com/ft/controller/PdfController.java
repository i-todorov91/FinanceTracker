package com.ft.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.User;
import com.ft.model.util.PdfCreator;

@Controller
public class PdfController {

	@RequestMapping(value="/login/createpdf", method=RequestMethod.GET)
	@ResponseBody
	public void createPdfGet(HttpSession session,HttpServletResponse res, @RequestParam("type") String type, Model model){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			try{
				String username = (String) session.getAttribute("username");
				User user = UserDAO.getInstance().getAllUsers().get(username);;
				PdfCreator pc = PdfCreator.getInstance();
				File file = null;
				
				if(type.equals("Budget")){
					Budget selectedBudget = (Budget) session.getAttribute("selectedBudget");
					file = pc.createBudgetPdf(user, "Budget name: " + selectedBudget.getName(), user.getBudgets().get(selectedBudget.getName()));
				}
				else if(type.equals("Account")){
					file = pc.CreateAccountInfoPdf(user);
				}
				else if(type.equals("Cashflow")){
					ArrayList<CashFlow> cashFlow = (ArrayList<CashFlow>) session.getAttribute("filteredData");
					file = pc.createCashFlowPdf(user, "Cashflow filtered data", cashFlow);
				} 
				else{
					
				}
				Files.copy(file.toPath(), (OutputStream)res.getOutputStream());
			} catch(Exception e){
				System.out.println("PdfView->GET: " + e.getMessage());
			}
		}
	}
	
	@RequestMapping(value="/login/viewpdf", method=RequestMethod.GET)
	public File viewPdfGet(@ModelAttribute("file") File file){
		
		return file;
	}
	
	@RequestMapping(value="/login/accountinformation", method=RequestMethod.GET)
	public String viewAccountInformationGet(HttpSession session){
		
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.setAttribute("url", "accountinformation.jsp");
			
		} 
		return "redirect: ../login";
	}
}
