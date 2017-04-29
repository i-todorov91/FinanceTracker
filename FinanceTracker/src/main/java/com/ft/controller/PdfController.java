package com.ft.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.User;
import com.ft.model.util.PdfCreator;

@Controller
public class PdfController {

	@RequestMapping(value="/pdftester", method=RequestMethod.GET)
	public String getPdf(HttpSession session){
	
		return "PDFtester";
		
	}
	
	@RequestMapping(value="/pdftester", method=RequestMethod.POST)
	public String postPdf(HttpSession session){
	
		PdfCreator pc = PdfCreator.getInstance();
		User user = null;
		try {
			user = UserDAO.getInstance().getAllUsers().get("zpetrov96@gmail.com");
			List<CashFlow> list = user.getBudgets().get("12").getIncomes();
//			pc.createCashFlowPdf(user, "Test Cash Flow PDF", list);
			pc.createBudgetPdf(user, "Test budget", user.getBudgets().get("12"));
		} catch (Exception e) {
			System.out.println("WE HAVE A PROBLEM !!");
			e.printStackTrace();
			return "PDFtester";
		}
		return "PDFtester";
	}
	
}
