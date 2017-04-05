package model.budget;

import java.util.ArrayList;
import java.util.Date;

import model.exceptions.InvalidCashFlowException;

public class Expense extends CashFlow{

	private static ArrayList<String> categories;
	
	public Expense(double quantity, Date date, String category) throws InvalidCashFlowException {
		super(quantity, date, category);
	}
	
	public void addCategorie(String category){
		this.categories.add(category);
	}

	@Override
	protected boolean validQuantity(double quantity) {
		if (quantity < 0) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean validCategory(String category) {
		if (categories.contains(category)) {
			return true;
		}
		return false;
	}	
	
}
