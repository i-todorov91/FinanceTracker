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
	@NotNull
	@Size(min=2, max=30)
	private String firstName;
	
	@NotNull
	@Size(min=2, max=30)
	private String lastName;
	
	private long id;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min=8, max=20)
	@Pattern(regexp="((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,})")
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
			this.firstName = firstName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(Validator.validateString(lastName)){
			this.lastName = lastName;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(Validator.isValidEmailAddress(email)){
			this.email = email;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// todo validate password 
		// strong password needed
		this.password = password;
	}

	public Map<String, Budget> getBudgets() {
		return budgets;
	}

	public void addBudget(Budget budget) {
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
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ "]";
	}

	
}
