package com.ft.model.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.ft.model.budget.Budget;
import com.ft.model.util.Validator;


public class User {
	
	@NotNull(message="Please, enter a valid email address!")
	@Email(message="Please, enter a valid email address!")
	private String email;
	
	@NotNull(message="Your first name must be between 2 and 30 characters long!")
	@Size(min=2, max=30, message="Your first name must be between 2 and 30 characters long!")
	private String firstName;
	
	@NotNull(message="Your last name must be between 2 and 30 characters long!")
	@Size(min=2, max=30, message="Your last name must be between 2 and 30 characters long!")
	private String lastName;
	
	private long id;
	
	@NotNull(message="Your password must be between 8 and 20 characters long and must contain at least 1 digit, 1 small letter, 1 capital letter, and 1 special symbol from these *^&+=")
	@Pattern(regexp="((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,20})", message="Your password must be between 8 and 20 characters long and must contain at least 1 digit, 1 small letter, 1 capital letter, and 1 special symbol from these *^&+=")
	private String password;
	
	private HashMap<String, Budget> budgets; //BudgetName -> Budget
	
	public User() {
		this.budgets = new HashMap<>();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(Validator.validateString(firstName)){
			this.firstName = firstName.trim();
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(Validator.validateString(lastName)){
			this.lastName = lastName.trim();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(Validator.isValidEmailAddress(email)){
			this.email = email.trim();
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public HashMap<String, Budget> getBudgets() {
		return budgets;
	}

	public synchronized void addBudget(Budget budget) {
		if (budgets.containsKey(budget.getName())) {
			System.out.println("Already has a budget with that name.");
			return;
		}
		this.budgets.put(budget.getName(), budget);
	}
	
	// no need for validation, we use the result from the database
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public double getBudgetsSum(){
		double result = 0;
		for(Budget i : budgets.values()){
			result += i.getBalance();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ "]";
	}
	
}
