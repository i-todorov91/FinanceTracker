package model.budget.flows;

import java.util.Date;

import model.util.exceptions.InvalidCashFlowException;

public abstract class CashFlow {

	protected long id;
	protected double quantity;
	protected Date date;
	protected Category category;
	protected TYPES type;
	public static enum TYPES { INCOME, EXPENSE };
	
	public CashFlow(double quantity, Date date, Category category, TYPES type) throws InvalidCashFlowException {
		if (category == null || !(validCategory(category) && validQuantity(quantity))) {
			throw new InvalidCashFlowException();
		}
		this.quantity = quantity;
		this.date = date;
		this.category = category;
		this.type = type;
	}
	
	protected abstract boolean validQuantity(double quantity);

	protected abstract boolean validCategory(Category category);
	
	// no need for validation because we use the result from the database
	public void setId(long id) {
		this.id = id;
	}
	
	public TYPES getType(){
		return type;
	}

	public long getId() {
		return id;
	}

	public double getQuantity() {
		return quantity;
	}

	public Date getDate() {
		return date;
	}
	
}
