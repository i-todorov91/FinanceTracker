package model.budget;

import java.util.ArrayList;
import java.util.Date;

import model.exceptions.InvalidCashFlowException;

public abstract class CashFlow {

	protected long id;
	protected double quantity;
	protected Date date;
	protected String category;
	
	public CashFlow(double quantity, Date date, String category) throws InvalidCashFlowException {
		if (!(validCategory(category) && validQuantity(quantity))) {
			throw new InvalidCashFlowException();
		}
		this.quantity = quantity;
		this.date = date;
		this.category = category;
	}
	
	protected abstract boolean validQuantity(double quantity);

	protected abstract boolean validCategory(String category);
	
	public void setId(long id) {
		this.id = id;
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
