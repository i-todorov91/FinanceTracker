package model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.DAO.CategoryDAO;
import model.budget.flows.Category;

public class Validator {
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private Validator(){
		
	}
	
	public static boolean isValidEmailAddress(String email) {
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
}
