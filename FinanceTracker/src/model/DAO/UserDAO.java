package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.budget.Budget;
import model.user.User;

public class UserDAO {
	private static UserDAO instance = null;
	private static final HashMap<String, User> allUsers = new HashMap<>();
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
				User x = new User(password, email);
				x.setFirstName(firstName);
				x.setLastName(secondName);
				x.setId(id);
				// get the budgets
				query = "SELECT b.id as id, b.name as name, b.balance as balance FROM budget INNER JOIN user_budget ON id = budget_id INNER JOIN user ON user_id = id";
				stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
				stmt.setLong(1, id);
				ResultSet rs1 = stmt.executeQuery();
				while(rs1.next()){
					String budgetName = rs1.getString("name");
					double budgetBalance = rs1.getDouble("balance");
					long budgetId = rs1.getLong("id");
					Budget y = new Budget(budgetName, budgetBalance);
					// for each budget select the incomes and expenses
					//query = "SELECT "
				}
			}
		} catch (SQLException e) {
			System.out.println("UserDAO: " + e.getMessage());
		}
	}
	public static UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public void addUser(User toAdd){
		// TODO
	}
	
	public Map<String, User> getAllUsers(){
		return java.util.Collections.unmodifiableMap(allUsers);
	}
}
