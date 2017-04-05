package model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.budget.Budget;

public class User {

	private String firstName;
	private String lastName;
	private long id;
	private String email;
	private String password;
	private ArrayList<Budget> budgets;
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.budgets = new ArrayList<>();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Budget> getBudgets() {
		return Collections.unmodifiableList(budgets);
	}

	public void addBudget(Budget budget) {
		this.budgets.add(budget);
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
}
