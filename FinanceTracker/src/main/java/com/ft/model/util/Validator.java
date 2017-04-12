package com.ft.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ft.model.DAO.CategoryDAO;
import com.ft.model.budget.flows.Category;

public class Validator {
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,})", Pattern.CASE_INSENSITIVE);
	private Validator(){
		
	}
	
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
	
	public static boolean validCategory(Category category){
		if (category != null && (CategoryDAO.getInstance().getAllDefaultCategories().containsKey(category.getName()) || CategoryDAO.getInstance().getAllCustomAddedCategories().containsKey(category.getName()))) {
			return true;
		}
		return false;
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
		return balance >= 0 ? true : false;
	}
	
	public static boolean isValidNumber(Object balance){
		try{
			double x = Double.parseDouble((String) balance);
		} catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
