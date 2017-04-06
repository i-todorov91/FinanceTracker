package model.budget.flows;

import java.util.ArrayList;
import java.util.Date;

import model.DAO.CategoryDAO;
import model.util.exceptions.InvalidCashFlowException;

public class Expense extends CashFlow{
	public Expense(double quantity, Date date, Category category) throws InvalidCashFlowException {
		super(quantity, date, category, CashFlow.TYPES.EXPENSE);
	}
}
