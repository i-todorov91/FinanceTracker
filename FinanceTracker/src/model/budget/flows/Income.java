package model.budget.flows;

import java.util.ArrayList;
import java.util.Date;

import model.util.exceptions.InvalidCashFlowException;

public class Income extends CashFlow {	
	public Income(double quantity, Date date, Category category) throws InvalidCashFlowException {
		super(quantity, date, category, CashFlow.TYPES.INCOME);
	}
}
