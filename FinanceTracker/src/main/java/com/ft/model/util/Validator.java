package com.ft.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ft.model.DAO.CategoryDAO;
import com.ft.model.budget.flows.Category;

public class Validator {
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,})", Pattern.CASE_INSENSITIVE);
	private Validator(){}
	
	public static boolean isValidEmailAddress(String email) {
		if(!validateString(email)){
			return false;
		}
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
	}
	
	public static boolean validateString(String str){
		return str == null || str.isEmpty() ? false : true;
	}
	
	public static boolean validateQuantity(double quantity){
		return quantity == 0 ? false : true;
	}
	
	public static boolean validPassword(String password){
		if(!validateString(password)){
			return false;
		}
		// must contain at least 1 digit
		// must contain at least 1 small letter
		// must contain at least 1 capital letter
		// must contain at least one of the symbols -> @#$%*^&+=
		// must be at least 8 symbols
		Matcher matcher = VALID_PASSWORD_REGEX .matcher(password);
		return matcher.find();
	}
	
	public static boolean validBalance(double balance){
		return balance >= 0 && balance <= Long.MAX_VALUE; 
	}
	
	public static boolean isValidBudget(String name, Object balance){
		return balance.toString().length() <= 10 && validateString(name) && name.length() >= 2 && name.length() <= 20;
	}
}
