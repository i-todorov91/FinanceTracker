package com.ft.model.budget.flows;

import java.sql.Date;

import com.ft.model.util.exceptions.InvalidCashFlowException;

public class Income extends CashFlow {	
	public Income(double quantity, Date date, Category category, String description) throws InvalidCashFlowException {
		super(quantity, date, category, CashFlow.TYPES.INCOME, description);
	}
}
