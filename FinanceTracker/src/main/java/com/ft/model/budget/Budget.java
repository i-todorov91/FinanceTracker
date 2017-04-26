package com.ft.model.budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ft.model.budget.flows.CashFlow;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.util.exceptions.InvalidBudgetException;

public class Budget {
	
	@NotNull
	@Size(min=2, max=15)
	private String name;
	private ArrayList<Expense> expenses;
	private ArrayList<Income> incomes;
	private double balance;
	private long id;
	
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
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public List<CashFlow> getExpenses(){
		return Collections.unmodifiableList(expenses);
	}
	
	public List<CashFlow> getIncomes(){
		return Collections.unmodifiableList(incomes);
	}
	
	public double getTotalIncome() {
		double totalIncome = 0;
		for (Income income : this.incomes) {
			totalIncome += Math.abs(income.getQuantity());
		}
		return totalIncome;		
	}
	
	public double getTotalExpense(){
		double totalExpense = 0;
		for (Expense expense : this.expenses) {
			totalExpense += Math.abs(expense.getQuantity());
		}
		return totalExpense;	
	}
	
	public double getIncomeForMonth(int month){
		double result = 0;
		for(CashFlow i : incomes){
			if(i.getDate().getMonth() + 1 == month){
				result += i.getQuantity();
			}
		}
		return result;
	}
	
	public double getExpenseForMonth(int month){
		double result = 0;
		for(CashFlow i : expenses){
			if(i.getDate().getMonth() + 1== month){
				result += i.getQuantity();
			}
		}
		return result;
	}
}
