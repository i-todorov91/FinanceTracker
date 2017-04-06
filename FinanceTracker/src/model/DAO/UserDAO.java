package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Statement;

import model.user.User;

public class UserDAO {
	private static UserDAO instance = null;
	private static final HashMap<String, User> allUsers = new HashMap<>();
	private UserDAO(){
		String query = "SELECT id, first_name, second_name, password, email FROM user";
		PreparedStatement stmt = null;
		try {
			stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.executeQuery();
			ResultSet rs = stmt.getGeneratedKeys();
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
				// TODO
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
