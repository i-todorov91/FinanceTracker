package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class demo {

	public static void main(String[] args) {
		
		System.out.println("test");
		
		try {
			String query = "SELECT id, first_name, second_name, password, email FROM user";
			PreparedStatement stmt = null;
			stmt = DBManager.getInstance().getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String firstName = rs.getString("first_name");
				System.out.println(firstName);
			}
			System.out.println("krai test");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}