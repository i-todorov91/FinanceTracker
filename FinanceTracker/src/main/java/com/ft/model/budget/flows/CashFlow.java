package com.ft.model.budget.flows;

import java.sql.Date;
import java.text.DecimalFormat;

import com.ft.model.util.Validator;
import com.ft.model.util.exceptions.InvalidCashFlowException;

public abstract class CashFlow implements Comparable<CashFlow>{

	private long id;
	private double quantity;
	private Date date;
	private String description;
	private Category category;
	private TYPES type;
	public static enum TYPES { INCOME, EXPENSE };
	
	public CashFlow(double quantity, Date date, Category category, TYPES type, String description) throws InvalidCashFlowException {

		if (!(Validator.validateQuantity(quantity))) {
			throw new InvalidCashFlowException();
		}
		this.quantity = quantity;
		this.date = date;
		this.category = category;
		this.type = type;
		this.description = description;
	}
	
	// no need for validation because we use the result from the database
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public TYPES getType(){
		return type;
	}

	public long getId() {
		return id;
	}

	public double getQuantity() {
		return Double.parseDouble(new DecimalFormat("#.00").format(quantity));
	}

	public Date getDate() {
		return date;
	}
	
	public Category getCategory(){
		return category;
	}
	
	@Override
	public int compareTo(CashFlow o) {
		if (this.getDate().before(o.getDate())) {
			return 1;
		} else if (this.getDate().after(o.getDate())) {
			return -1;
		} else {
			if ((this.getQuantity() - o.getQuantity()) > 0) {
				return 1;
			} else if ((this.getQuantity() - o.getQuantity()) < 0) {
				return -1;
			} else {
				if ((this.getDescription().compareTo(o.getDescription()) > 0)) {
					return 1;
				} else if ((this.getDescription().compareTo(o.getDescription()) < 0)) {
					return -1;
				} else {
					if ((this.getId() > this.getId())) {
						return 1;
					} else if ((this.getId() < this.getId())) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		}
	}
	
}
