package com.ft.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;

import com.ft.model.DAO.EmailSender;
import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.user.User;

public class EmailDaemonThread extends Thread {
	
	private EmailDaemonThread() {}
	
	static{
		System.out.println("STATIC METHOD _____________________________________________________________________________");
		EmailDaemonThread edt = new EmailDaemonThread();
		edt.start();
	}

	@Override
	public void run() {
//		HashMap<String, User> allUsers = (HashMap<String, User>) UserDAO.getInstance().getAllUsers();
//		while(true){
//			
//			for (Entry<String, User> entryUser : allUsers.entrySet()) {
//				
//				boolean active = false;
//				String email = entryUser.getKey();
//				User user = entryUser.getValue();
//				Map<String, Budget> budgets = user.getBudgets();
//				
//				for (Budget budget: budgets.values()) {
//					
//					Income income = (Income) budget.getIncomes().get(0);
//					Expense expense = (Expense) budget.getExpenses().get(0);
//					
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(new Date());
//
//					// substract 4 days
//					// If we give 5 there it will give 6 days back
//					cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-4);
//
//					Date checkDate = cal.getTime();
//					
//					if (income.getDate().after(checkDate)) {
//						active = true;
//						break;
//					}
//					if (expense.getDate().after(checkDate)) {
//						active = true;
//						break;
//					}
//				}
//				
//				if (!active) {
//					try {
//						EmailSender.getInstance().notify(email);
//					} catch (MessagingException e) {
//						System.out.println("EmailDaemondThread: " + e.getMessage());
//					}
//				}
//				
//			}
//		}
		while(true){
			System.out.println("Az sym Daemon Thread i se kefq che jiveq");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Daemon Thread is interupted");
			}
		}
	}
	
}
