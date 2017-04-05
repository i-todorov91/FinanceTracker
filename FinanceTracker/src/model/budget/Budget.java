package model.budget;

import java.util.ArrayList;

import model.budget.flows.CashFlow;
import model.budget.flows.Expense;
import model.budget.flows.Income;
import model.exceptions.InvalidBudgetException;

public class Budget {

	private String name;
	private ArrayList<Expense> expenses;
	private ArrayList<Income> incomes;
	private double balance;
	
	public Budget(String name) throws InvalidBudgetException{
		if (name == null || name.isEmpty()) {
			throw new InvalidBudgetException();
		}
		this.name = name;
		this.expenses = new ArrayList<>();
		this.incomes = new ArrayList<>();
		this.balance = 0;
	}
	
	public void addCashFlow(CashFlow flow){
		if (flow.getType() == CashFlow.TYPES.INCOME) {
			this.incomes.add((Income)flow);
		} else {
			this.expenses.add((Expense)flow);
		}
		this.balance += flow.getQuantity();
	}
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
	
}
