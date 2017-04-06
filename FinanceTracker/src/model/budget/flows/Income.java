package model.budget.flows;

import java.util.ArrayList;
import java.util.Date;

import model.util.exceptions.InvalidCashFlowException;

public class Income extends CashFlow {
	
	private static ArrayList<Category> categories = new ArrayList<>();
	
	public Income(double quantity, Date date, Category category) throws InvalidCashFlowException {
		super(quantity, date, category, CashFlow.TYPES.INCOME);
	}
	
	public static void addCategorie(Category category){
		if(category != null && !categories.contains(category)){
			Income.categories.add(category);
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
