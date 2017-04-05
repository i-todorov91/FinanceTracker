package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
	
	private static DBManager db = null;
	private Connection con = null;

	private DBManager(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			if(System.getProperty("os.name").startsWith("Linux")){
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financeTracker?autoReconnect=true&useSSL=false", "root", "14eiuqhwdyeuQ*");
			}
			else{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financeTracker?autoReconnect=true&useSSL=false", "root", "injikipliok");
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public synchronized Connection getConnection(){
		if(db == null){
			getInstance();
		}
		return DBManager.db.con;
	}
	
	public synchronized static DBManager getInstance(){
		if(DBManager.db == null){
			DBManager.db = new DBManager();
		}
		return DBManager.db;
	}

}
