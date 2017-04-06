package model.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.budget.Budget;
import model.budget.flows.Category;
import model.budget.flows.Expense;
import model.budget.flows.Income;
import model.user.User;
import model.util.exceptions.InvalidCashFlowException;

public class UserDAO {
	private static UserDAO instance = null;
	private static final HashMap<String, User> allUsers = new HashMap<>();
	@SuppressWarnings("static-access")
	private UserDAO(){
		String query = "SELECT id, first_name, second_name, password, email FROM user";
		PreparedStatement stmt = null;
		try {
			stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				long id = rs.getLong("id");
				User user = new User(password, email);
				user.setFirstName(firstName);
				user.setLastName(secondName);
				user.setId(id);
				
				// get the budgets
				query = "SELECT b.id as id, b.name as name, b.balance as balance FROM budget INNER JOIN user_budget ON id = budget_id INNER JOIN user ON user_id = id";
				stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
				stmt.setLong(1, id);
				ResultSet rs1 = stmt.executeQuery();
				while(rs1.next()){
					String budgetName = rs1.getString("name");
					double budgetBalance = rs1.getDouble("balance");
					long budgetId = rs1.getLong("id");
					Budget budget = new Budget(budgetName, budgetBalance);
					// for each budget select the incomes and expenses
					
					// find all incomes
					query = "SELECT quantity, date, c.name, i.name AS icon FROM cash_flow cash INNER JOIN income inc ON cash.id = inc.cash_flow_id INNER JOIN budget_income bi ON bi.income_id = inc.id INNER JOIN budget bt ON bi.budget_id = bt.id INNER JOIN user_budget ubt ON bt.id = ubt.user_id INNER JOIN category c ON c.id = inc.category INNER JOIN default_icon i ON c.icon_id = i.id WHERE ubt.user_id = id";
					stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
					stmt.setLong(1, id);
					ResultSet rs2 = stmt.executeQuery();
					while(rs2.next()){
						double quantity = rs2.getDouble("quantity");
						Date date = rs2.getDate(2);
						String categoryName = rs2.getString("name");
						String categoryIcon = rs2.getString("icon");
						try {
							budget.addCashFlow(new Income(quantity, date, new Category(categoryName, categoryIcon)));
						} catch (InvalidCashFlowException e) {
							System.out.println("UserDAO->Budget->Income->	Category: " + e.getMessage());
						}
					}
					
					// find all expenses
					query = "SELECT quantity, date, c.name, i.name AS icon FROM cash_flow cash INNER JOIN expense exp ON cash.id = exp.cash_flow_id INNER JOIN budget_income bi ON bi.income_id = exp.id INNER JOIN budget bt ON bi.budget_id = bt.id INNER JOIN user_budget ubt ON bt.id = ubt.user_id INNER JOIN category c ON c.id = exp.category INNER JOIN default_icon i ON c.icon_id = i.id WHERE ubt.user_id = id";
					stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
					stmt.setLong(1, id);
					ResultSet rs3 = stmt.executeQuery();
					while(rs2.next()){
						double quantity = rs3.getDouble("quantity");
						Date date = rs3.getDate(2);
						String categoryName = rs3.getString("name");
						String categoryIcon = rs3.getString("icon");
						try {
							budget.addCashFlow(new Expense(quantity, date, new Category(categoryName, categoryIcon)));
						} catch (InvalidCashFlowException e) {
							System.out.println("UserDAO->Budget->Income->	Category: " + e.getMessage());
						}
					}
					
					// add the budget to the user
					user.addBudget(budget);
				}
			}
		} catch (SQLException e) {
			System.out.println("UserDAO: " + e.getMessage());
		}
	}
	public synchronized static UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public synchronized void addUser(User toAdd){
		// TODO
	}
	
	public Map<String, User> getAllUsers(){
		return Collections.unmodifiableMap(allUsers);
	}
}
