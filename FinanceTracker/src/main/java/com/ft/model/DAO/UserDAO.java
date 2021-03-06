package com.ft.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.Category;
import com.ft.model.budget.flows.Expense;
import com.ft.model.budget.flows.Income;
import com.ft.model.user.User;
import com.ft.model.util.exceptions.InvalidCashFlowException;
import com.ft.model.util.exceptions.InvalidEncryptionException;

public class UserDAO {
	private static UserDAO instance = null;
	private static final HashMap<String, User> allUsers = new HashMap<>(); // email -> User
	private static Connection con = DBManager.getInstance().getConnection();
	
	public synchronized static UserDAO getInstance() throws SQLException, InvalidCashFlowException{
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	private UserDAO() throws SQLException, InvalidCashFlowException{
		String query = "SELECT id, first_name, second_name, password, email FROM user";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				long id = rs.getLong("id");
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setFirstName(firstName);
				user.setLastName(secondName);
				user.setId(id);
				
				// get the budgets
				query = "SELECT b.id as id, b.name as name, b.balance as balance FROM budget b JOIN user_budget ub ON id = budget_id WHERE ub.user_id = ?";
				stmt = con.prepareStatement(query);
				stmt.setLong(1, id);
				ResultSet rs1 = stmt.executeQuery();
				while(rs1.next()){
					String budgetName = rs1.getString("name");
					//budget concrete balance before adding additional incomes and expenses !!
					double budgetBalance = rs1.getDouble("balance");
					long budgetId = rs1.getLong("id");
					Budget budget = new Budget(budgetName, budgetBalance);

					budget.setId(budgetId);
					// for each budget select the incomes and expenses
					
					// find all incomes
					query = "SELECT quantity, date, description, c.name, i.name AS icon FROM cash_flow cash JOIN income inc ON cash.id = inc.cash_flow_id JOIN budget_income bi ON bi.income_id = inc.id JOIN budget bt ON bi.budget_id = bt.id JOIN category c ON c.id = inc.category JOIN default_icon i ON c.icon_id = i.id WHERE bt.id = ?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budgetId);
					ResultSet rs2 = stmt.executeQuery();
					while(rs2.next()){
						double quantity = rs2.getDouble("quantity");
						Date date = rs2.getDate(2);
						String categoryName = rs2.getString("name");
						String categoryIcon = rs2.getString("icon");
						String description = rs2.getString("description");
						try {
							budget.addCashFlow(new Income(quantity, date, new Category(categoryName, categoryIcon, Category.TYPE.INCOME), description));
						} catch (InvalidCashFlowException e) {
							System.out.println("UserDAO->Budget->Income->	Category: " + e.getMessage());
							throw e;
						}
					}
					
					// find all expenses
					query = "SELECT quantity, date, description, c.name, i.name AS icon FROM cash_flow cash JOIN expense exp ON cash.id = exp.cash_flow_id JOIN budget_expense bi ON bi.expense_id = exp.id JOIN budget bt ON bi.budget_id = bt.id JOIN category c ON c.id = exp.category JOIN default_icon i ON c.icon_id = i.id WHERE bt.id = ?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budgetId);
					ResultSet rs3 = stmt.executeQuery();
					while(rs3.next()){
						double quantity = rs3.getDouble("quantity");
						Date date = rs3.getDate(2);
						String categoryName = rs3.getString("name");
						String categoryIcon = rs3.getString("icon");
						String description = rs3.getString("description");
						try {
							budget.addCashFlow(new Expense(quantity, date, new Category(categoryName, categoryIcon, Category.TYPE.EXPENSE), description));
						} catch (InvalidCashFlowException e) {
							System.out.println("UserDAO->Budget->Income->Category: " + e.getMessage());
							throw e;
						}
					}
					//correcting balance after adding incomes and expenses
					budget.setBalance(budgetBalance);
					// add the budget to the user
					user.addBudget(budget);
				}
				allUsers.put(user.getEmail(), user);
			}
		} catch (SQLException e) {
			System.out.println("UserDAO: Constructor -" + e.getMessage());
			throw e;
		}
	}
	
	public synchronized boolean addUser(User toAdd) throws SQLException{
		if(allUsers.containsKey(toAdd.getEmail())){
			return false;
		}
		long id = 0;
		PreparedStatement stmt = null;
		String query = "SELECT PASSWORD(?) AS pass";
		String pass = null;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, toAdd.getPassword());
			ResultSet res = stmt.executeQuery();
			res.next();
			pass = res.getString("pass");
		} catch (SQLException e1) {
			System.out.println("UserDAO->getPass: " + e1.getMessage());
			throw e1;
		}
		query = "INSERT INTO user(first_name, second_name, password, email) VALUES(?, ?, ?, ?)";
		try {
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, toAdd.getFirstName());
			stmt.setString(2, toAdd.getLastName());
			stmt.setString(3, pass);
			stmt.setString(4, toAdd.getEmail());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
		} catch (SQLException e) {
			System.out.println("UserDAO->addUser: " + e.getMessage());
			throw e;
		}
		toAdd.setId(id);
		toAdd.setPassword(pass);
		allUsers.put(toAdd.getEmail(), toAdd);
		return true;
	}

	public synchronized boolean addBudget(Budget toAdd, User user) throws SQLException{
		if(allUsers.containsKey(user.getEmail())){
			if(user.getBudgets().containsKey(toAdd.getName())){
				return false;
			}
			String query = "";
			PreparedStatement stmt = null;
			long id = 0;
			try {
				// insert in budget table and get the budget id
				con.setAutoCommit(false);
				query = "INSERT IGNORE INTO budget(name, balance) VALUES(?, ?)";
				stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, toAdd.getName());
				stmt.setDouble(2, toAdd.getBalance());
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				
				// insert in user_budget table
				query = "INSERT IGNORE INTO user_budget(user_id, budget_id) VALUES(?, ?)";
				stmt = con.prepareStatement(query);
				stmt.setLong(1, user.getId());
				stmt.setLong(2, id);
				stmt.executeUpdate();
				
				// add the budget to the user in the HashMap<String, User>
				toAdd.setId(id);
				allUsers.get(user.getEmail()).addBudget(toAdd);
				con.commit();
				return true;
			} catch (SQLException e) {
				System.out.println("UserDAO->addBudget: " + e.getMessage());
				try {
					con.rollback();
				} catch (SQLException e1) {
					System.out.println("UserDAO->addBudget->rollBack: " + e1.getMessage());
					throw e1;
				}
				throw e;
			} finally {
				try {
					con.setAutoCommit(true);
				} catch (SQLException e) {
					System.out.println("UserDAO->addBudget->setAutoCommit(true): " + e.getMessage());
					throw e;
				}
			}
		}
		return false;
	}

	public synchronized boolean addIncome(Income toAdd, long budgetId, String username) throws SQLException{
	
		String query = "SELECT name FROM budget WHERE id = ?";
		String budgetName = null;
		Budget budget = null;
		PreparedStatement stmt = null;
		long cashFlowId = 0;
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setLong(1, budgetId);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				budgetName = rs.getString("name");
				budget = getAllUsers().get(username).getBudgets().get(budgetName);
			} else {
				System.out.println("UserDAO->addIncome : There is no such budget!");
				return false;
			}
			
			con.setAutoCommit(false);
		
			//insert into cash_flow & get id
			query = "INSERT IGNORE INTO cash_flow(quantity, date, description) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, toAdd.getQuantity());
			stmt.setDate(2, new java.sql.Date(toAdd.getDate().getTime()));
			stmt.setString(3, toAdd.getDescription());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			cashFlowId = rs.getLong(1);
			
			// insert into income table
			query = "INSERT IGNORE INTO income(id, category, cash_flow_id) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cashFlowId);
			stmt.setLong(2, toAdd.getCategory().getId());
			stmt.setLong(3, cashFlowId);
			stmt.executeUpdate();
			
			// insert into budget_income table
			query = "INSERT IGNORE INTO budget_income(budget_id, income_id) VALUES(?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setLong(1, budgetId);
			stmt.setLong(2, cashFlowId);
			stmt.executeUpdate();
			
			//add to budget, get budget new balance, update budget balance
			budget.addCashFlow(toAdd);
			double newBalance = budget.getBalance();
			query = "UPDATE budget SET balance = ? WHERE id = ?";
			stmt = con.prepareStatement(query);
			stmt.setDouble(1, newBalance);
			stmt.setLong(2, budgetId);
			stmt.executeUpdate();
			
			con.commit();
			
			return true;
		} catch (SQLException e) {
			System.out.println("UserDAO->addIncome: " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("UserDAO->addIncome->rollBack: " + e1.getMessage());
				throw e1;
			}
			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("UserDAO->addIncome->setAutoCommit(true): " + e.getMessage());
				throw e;
			}
		}
	}
	
	public synchronized boolean addExpense(Expense toAdd, long budgetId, String username) throws SQLException{
	
		String query = "SELECT name FROM budget WHERE id = ?";
		String budgetName = null;
		Budget budget = null;
		PreparedStatement stmt = null;
		long cashFlowId = 0;
		try {
			stmt = con.prepareStatement(query);
			stmt.setLong(1, budgetId);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				budgetName = rs.getString("name");
				budget = getAllUsers().get(username).getBudgets().get(budgetName);
			} else {
				System.out.println("UserDAO->addExpense : There is no such budget!");
				return false;
			}
			
			con.setAutoCommit(false);
			
			//insert into cash_flow & get id
			query = "INSERT IGNORE INTO cash_flow(quantity, date, description) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, toAdd.getQuantity());
			stmt.setDate(2, new java.sql.Date(toAdd.getDate().getTime()));
			stmt.setString(3, toAdd.getDescription());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			cashFlowId = rs.getLong(1);
			
			// insert into expense table
			query = "INSERT IGNORE INTO expense(id, category, cash_flow_id) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cashFlowId);
			stmt.setLong(2, toAdd.getCategory().getId());
			stmt.setLong(3, cashFlowId);
			stmt.executeUpdate();
			
			// insert into budget_expense table
			query = "INSERT IGNORE INTO budget_expense(budget_id, expense_id) VALUES(?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setLong(1, budgetId);
			stmt.setLong(2, cashFlowId);
			stmt.executeUpdate();
			
			//add to budget, get budget new balance, update budget balance
			budget.addCashFlow(toAdd);
			double newBalance = budget.getBalance();
			query = "UPDATE budget SET balance = ? WHERE id = ?";
			stmt = con.prepareStatement(query);
			stmt.setDouble(1, newBalance);
			stmt.setLong(2, budgetId);
			stmt.executeUpdate();
			
			con.commit();
			
			return true;
		} catch (SQLException e) {
			System.out.println("UserDAO->addExpense: " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("UserDAO->addExpense->rollBack: " + e1.getMessage());
				throw e1;
			}
			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("UserDAO->addExpense->setAutoCommit(true): " + e.getMessage());
				throw e;
			}
		}
	}
	
	public synchronized void removeBudget(String username, String budgetName) throws SQLException{
		
		// check if the budgetName is valid
		User user = allUsers.get(username);
		if(user.getBudgets().containsKey(budgetName)){
				Budget budget = user.getBudgets().get(budgetName);
				
				try{
					// remove from database
					String query = "";
					String subquery = "";
					PreparedStatement stmt = null;
					PreparedStatement stmt1 = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					con.setAutoCommit(false);
					
					// get the expenses id's for the budget
					query = "SELECT expense_id FROM budget_expense WHERE budget_id=?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budget.getId());
					rs = stmt.executeQuery();
					while(rs.next()){
						long expenseId = rs.getLong("expense_id");
						
						// for each expense id delete it from the budget_expense
						subquery = "DELETE FROM budget_expense WHERE expense_id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, expenseId);
						stmt1.executeUpdate();
						
						// delete the expense
						subquery = "DELETE FROM expense WHERE id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, expenseId);
						stmt1.executeUpdate();
						
						// delete the cashflow
						subquery = "DELETE FROM cash_flow WHERE id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, expenseId);
						stmt1.executeUpdate();
					}

					// remove from budget_income
					query = "SELECT income_id FROM budget_income WHERE budget_id=?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budget.getId());
					rs = stmt.executeQuery();
					while(rs.next()){
						long incomeId = rs.getLong("income_id");
						
						// for each income id delete it from the budget_income
						subquery = "DELETE FROM budget_income WHERE income_id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, incomeId);
						stmt1.executeUpdate();
						
						// delete the income
						subquery = "DELETE FROM income WHERE id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, incomeId);
						stmt1.executeUpdate();
						
						// delete the cashflow
						subquery = "DELETE FROM cash_flow WHERE id=?";
						stmt1 = con.prepareStatement(subquery);
						stmt1.setLong(1, incomeId);
						stmt1.executeUpdate();
					}
					
					// remove from user_budget
					query = "DELETE FROM user_budget WHERE budget_id=?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budget.getId());
					stmt.executeUpdate();
					
					// remove from budget
					query = "DELETE FROM budget WHERE id=?";
					stmt = con.prepareStatement(query);
					stmt.setLong(1, budget.getId());
					stmt.executeUpdate();
					
					// remove from the user's ArrayList of budgets
					user.getBudgets().remove(budget.getName());
					
					con.commit();
					
				} catch(SQLException e){
					System.out.println("UserDAO->removeBudget: " + e.getMessage());
					try {
						con.rollback();
					} catch (SQLException e1) {
						System.out.println("UserDAO->removeBudget->rollBack: " + e1.getMessage());
						throw e1;
					}
					throw e;
				} finally {
					try {
						con.setAutoCommit(true);
					} catch (SQLException e) {
						System.out.println("UserDAO->removeBudget->setAutoCommit(true): " + e.getMessage());
						throw e;
					}
				}
		}
	}
	
	public boolean validLogin(String email, String password) throws SQLException{
		if(!allUsers.containsKey(email)){
			return false;
		} 
		else{
			try {
				String query = "SELECT PASSWORD(?) AS pass";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, password);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				String pass = rs.getString("pass");
				if(allUsers.get(email).getPassword().equals(pass)){
					return true;
				}
				else{
					return false;
				}
			} catch (SQLException e) {
				System.out.println("UserDAO->validLogin: " + e.getMessage());
				throw e;
			}
		}
	}
	
	public Map<String, User> getAllUsers(){
		return Collections.unmodifiableMap(allUsers);
	}
	
}
