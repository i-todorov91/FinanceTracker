package model.budget.flows;

import java.util.ArrayList;
import java.util.Date;

import model.util.exceptions.InvalidCashFlowException;

public class Expense extends CashFlow{

	private static ArrayList<Category> categories = new ArrayList<>();
	
	public Expense(double quantity, Date date, Category category) throws InvalidCashFlowException {
		super(quantity, date, category, CashFlow.TYPES.EXPENSE);
	}
	
	public static void addCategorie(Category category){
		if(category != null && !categories.contains(category)){
			Expense.categories.add(category);
		}
	}

	@Override
	protected boolean validCategory(Category category) {
		if (categories != null && categories.contains(category)) {
			return true;
		}
		return false;
	}	
	
}
