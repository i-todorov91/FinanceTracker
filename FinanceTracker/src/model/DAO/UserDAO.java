package model.DAO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.mysql.jdbc.Statement;

import model.budget.Budget;
import model.budget.flows.Category;
import model.budget.flows.Expense;
import model.budget.flows.Income;
import model.user.User;
import model.util.StringUtil;
import model.util.exceptions.InvalidCashFlowException;
import model.util.exceptions.InvalidEncryptionException;

public class UserDAO {
	private static UserDAO instance = null;
	private static final HashMap<String, User> allUsers = new HashMap<>();
	@SuppressWarnings("static-access")
	private UserDAO(){
		// TODO add transactions
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
				User user = new User(email, password);
				user.setFirstName(firstName);
				user.setLastName(secondName);
				user.setId(id);
				
				// get the budgets
				query = "SELECT b.id as id, b.name as name, b.balance as balance FROM budget b INNER JOIN user_budget ON id = budget_id WHERE user_budget.user_id = ?";
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
					query = "SELECT quantity, date, c.name, i.name AS icon FROM cash_flow cash INNER JOIN income inc ON cash.id = inc.cash_flow_id INNER JOIN budget_income bi ON bi.income_id = inc.id INNER JOIN budget bt ON bi.budget_id = bt.id INNER JOIN category c ON c.id = inc.category INNER JOIN default_icon i ON c.icon_id = i.id WHERE bt.id = ?";
					stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
					stmt.setLong(1, budgetId);
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
					query = "SELECT quantity, date, c.name, i.name AS icon FROM cash_flow cash INNER JOIN expense exp ON cash.id = exp.cash_flow_id INNER JOIN budget_income bi ON bi.income_id = exp.id INNER JOIN budget bt ON bi.budget_id = bt.id INNER JOIN category c ON c.id = exp.category INNER JOIN default_icon i ON c.icon_id = i.id WHERE bt.id = ?";
					stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
					stmt.setLong(1, budgetId);
					ResultSet rs3 = stmt.executeQuery();
					while(rs2.next()){
						double quantity = rs3.getDouble("quantity");
						Date date = rs3.getDate(2);
						String categoryName = rs3.getString("name");
						String categoryIcon = rs3.getString("icon");
						try {
							budget.addCashFlow(new Expense(quantity, date, new Category(categoryName, categoryIcon)));
						} catch (InvalidCashFlowException e) {
							System.out.println("UserDAO->Budget->Income->Category: " + e.getMessage());
						}
					}
					
					// add the budget to the user
					user.addBudget(budget);
				}
				allUsers.put(user.getEmail(), user);
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
	
	@SuppressWarnings("static-access")
	public synchronized boolean addUser(User toAdd){
		if(allUsers.containsKey(toAdd.getEmail())){
			return false;
		}
		long id = 0;
		PreparedStatement stmt = null;
		String query = "INSERT INTO user(first_name, second_name, password, email) VALUES(?, ?, ?, ?)";
		try {
			stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, toAdd.getFirstName());
			stmt.setString(2, toAdd.getLastName());
			stmt.setString(3, StringUtil.getInstance().encrypt(toAdd.getPassword()));
			stmt.setString(4, toAdd.getEmail());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
		} catch (SQLException e) {
			System.out.println("UserDAO->addUser: " + e.getMessage());
			return false;
		}
		toAdd.setId(id);
		allUsers.put(toAdd.getEmail(), toAdd);
		return true;
	}
/*	
	public synchronized boolean addBudget(Budget toAdd, long userId){
		
	}
	
	public synchronized boolean addIncome(Income toAdd, long budgetId, long userId){
		
	}
	
	public synchronized boolean addExpense(Expense toAdd, long budgetId, long userId){
		
	}
*/
	public boolean validLogin(String email, String password){
		if(!allUsers.containsKey(email)){
			return false;
		} else
			try {
				if(StringUtil.getInstance().decrypt(allUsers.get(email).getPassword()).equals(password)){
					return true;
				}
				else{
					return false;
				}
			} catch (InvalidEncryptionException e) {
				System.out.println("UserDAO->validLogin: " + e.getMessage());
				return false;
			}
	}
	
	public Map<String, User> getAllUsers(){
		return Collections.unmodifiableMap(allUsers);
	}
}
