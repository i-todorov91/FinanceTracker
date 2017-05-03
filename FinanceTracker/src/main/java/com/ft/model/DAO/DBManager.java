package com.ft.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
	
	private static DBManager db = null;
	private Connection con = null;

	private DBManager(){
		
		try{
	        Class.forName("com.mysql.jdbc.Driver");
	        
			if(System.getProperty("os.name").startsWith("Linux")){
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker?autoReconnect=true&useSSL=false", "root", "14eiuqhwdyeuQ*");
			}
			else{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker?autoReconnect=true&useSSL=false", "root", "injikipliok");
			}
		}catch(Exception ex){
			System.out.println("DBManager: " + ex.getMessage());
		}
	}
	
	public synchronized Connection getConnection() {
		if(db == null){
			getInstance();
		}
		return con;
	}
	
	public synchronized static DBManager getInstance() {
		if(db == null){
			db = new DBManager();
		}
		return db;
	}

}
