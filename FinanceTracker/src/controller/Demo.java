package controller;

import java.util.Date;
import java.util.Map.Entry;

import model.budget.Budget;
import model.budget.flows.Category;
import model.budget.flows.Income;
import model.exceptions.InvalidBudgetException;
import model.exceptions.InvalidCashFlowException;
import model.user.User;

public class Demo {

	public static void main(String[] args) throws InvalidBudgetException, InvalidCashFlowException {
		User zaki = new User("zpetrov96@gmail.com", "mysecretpassword");
		zaki.addBudget(new Budget("mybudget"));
		zaki.addBudget(new Budget("mybudget1", 30));
		Income.addCategorie(new Category("zaki", "na zaki ikonata"));
		for(Entry<String, Budget> i : zaki.getBudgets().entrySet()){
			i.getValue().addCashFlow(new Income(2, new Date(1213123), new Category("zaki", "na zaki ikonata")));
			System.out.println(i.getValue().getBalance());
		}
	}

}

