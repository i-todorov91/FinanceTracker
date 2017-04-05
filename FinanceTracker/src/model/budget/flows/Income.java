package model.budget.flows;

import java.util.ArrayList;
import java.util.Date;

import model.exceptions.InvalidCashFlowException;

public class Income extends CashFlow {
	
	private static ArrayList<Category> categories;
	
	public Income(double quantity, Date date, Category category) throws InvalidCashFlowException {
		super(quantity, date, category);
		this.categories = new ArrayList<>();
	}
	
	public void addCategorie(Category category){
		if(category != null && !validCategory(category)){
			this.categories.add(category);
		}
	}

	@Override
	protected boolean validQuantity(double quantity) {
		if (quantity > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean validCategory(Category category) {
		if (categories.contains(category)) {
			return true;
		}
		return false;
	}
	
}
