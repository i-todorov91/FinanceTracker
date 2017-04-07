package model.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.budget.Budget;
import model.util.Validator;

public class User {
	private String firstName;
	private String lastName;
	private long id;
	private String email;
	private String password;
	private HashMap<String, Budget> budgets; //BudgetName -> Budget
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
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
		return Collections.unmodifiableMap(budgets);
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
