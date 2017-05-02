package com.ft.controller.threads;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ft.model.DAO.UserDAO;
import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.user.User;
import com.ft.model.util.EmailSender;

@Component
public class EmailDaemonThread extends Thread {

	private static final long SLEEP_TIME = 24*60*60*1000; //1 day
	private static EmailSender emailSender = EmailSender.getInstance();
	
	@Async
	@Override
	public void run() {
		
		Map<String, User> allUsers = null;
		try {
			allUsers = UserDAO.getInstance().getAllUsers();
		} catch (Exception e) {
			System.out.println("EmailDaemonThread -> getAllUsers: " + e.getMessage());
		}
		while(true){
			
			for (Entry<String, User> entryUser : allUsers.entrySet()) {
				
				boolean active = false;
				String email = entryUser.getKey();
				User user = entryUser.getValue();
				Map<String, Budget> budgets = user.getBudgets();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				// substract 4 days
				// If we give 5 there it will give 6 days back
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 4);
				Date checkDate = cal.getTime();
				TreeSet<CashFlow> sordedCF = new TreeSet<>();
			
				for (Budget budget: budgets.values()) {
					
					//check all incomes
					Income income = null;
					if (!budget.getIncomes().isEmpty()) {
						sordedCF.addAll(budget.getIncomes());
						income = (Income) sordedCF.first();
						if (income.getDate().after(checkDate)) {
							active = true;
							break;
						}
					}
					
					//check all expenses
					Expense expense = null;
					if (!budget.getExpenses().isEmpty()) {
						sordedCF.addAll(budget.getExpenses());
						expense = (Expense) sordedCF.first();
						if (expense.getDate().after(checkDate)) {
							active = true;
							break;
						}	
					}
					
				}
				
				if (!active) {
					try {
						emailSender.notify(email);
						System.out.println("Pratih email na " + email);
					} catch (MessagingException e) {
						System.out.println("EmailDaemondThread: " + e.getMessage());
					}
				}
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.out.println("EmailDaemondThread -> interupted !");
				break;
			}
		}	
	}
	
}
