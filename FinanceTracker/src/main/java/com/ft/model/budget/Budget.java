package com.ft.model.budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ft.model.budget.flows.CashFlow;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.util.exceptions.InvalidBudgetException;

public class Budget {

	private String name;
	private ArrayList<Expense> expenses;
	private ArrayList<Income> incomes;
	private double balance;
	
	public Budget(String name) throws InvalidBudgetException{
		this(name, 0);
	}
	
	public Budget(String name, double balance) {
		this.name = name;
		this.expenses = new ArrayList<>();
		this.incomes = new ArrayList<>();
		this.balance = balance; // the person may have some money in the budget as start
	}
	
	public void addCashFlow(CashFlow flow){
		if (flow.getType() == CashFlow.TYPES.INCOME) {
			this.incomes.add((Income)flow);
			this.balance += flow.getQuantity();
		} else {
			this.expenses.add((Expense)flow);
			this.balance -= flow.getQuantity();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public List<CashFlow> getExpenses(){
		return Collections.unmodifiableList(expenses);
	}
	
	public List<CashFlow> getIncomes(){
		return Collections.unmodifiableList(incomes);
	}
	
}
