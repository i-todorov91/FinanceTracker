package controller;

import java.util.Date;
import java.util.Map.Entry;

import model.budget.Budget;
import model.budget.flows.Category;
import model.budget.flows.Income;
import model.user.User;
import model.util.Validator;
import model.util.exceptions.InvalidBudgetException;
import model.util.exceptions.InvalidCashFlowException;
import model.util.exceptions.InvalidEmailException;

public class Demo {

	public static void main(String[] args) throws InvalidBudgetException, InvalidCashFlowException, InvalidEmailException {
		User zaki = new User("ribinasdasd@uni-sofia.bg", "mysecretpassword");
		System.out.println(Validator.isValidEmailAddress(zaki.getEmail()));
		zaki.addBudget(new Budget("mybudget"));
		zaki.addBudget(new Budget("mybudget1", 30));
		for(Entry<String, Budget> i : zaki.getBudgets().entrySet()){
			i.getValue().addCashFlow(new Income(2, new Date(1213123), new Category("Food", "nqkwa random icona(ne proverqwame po ime na ikona)")));
			// working
			System.out.println(i.getValue().getBalance());
		}
	}

}

