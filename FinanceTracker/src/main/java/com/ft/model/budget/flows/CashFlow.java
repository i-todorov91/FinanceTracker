package com.ft.model.budget.flows;

import java.util.Date;

import com.ft.model.util.Validator;
import com.ft.model.util.exceptions.InvalidCashFlowException;

public abstract class CashFlow {

	private long id;
	private double quantity;
	private Date date;
	private Category category;
	private TYPES type;
	public static enum TYPES { INCOME, EXPENSE };
	
	public CashFlow(double quantity, Date date, Category category, TYPES type) throws InvalidCashFlowException {
		if (!(Validator.validCategory(category)) || !(Validator.validateQuantity(quantity))) {
			throw new InvalidCashFlowException();
		}
		this.quantity = quantity;
		this.date = date;
		this.category = category;
		this.type = type;
	}
	
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
	
	public Category getCategory(){
		return category;
	}
	
}
