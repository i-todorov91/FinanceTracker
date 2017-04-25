package com.ft.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ft.model.budget.flows.Category;
import com.ft.model.util.Validator;
import com.ft.model.util.exceptions.InvalidCashFlowException;

public class CategoryDAO {
	
	private static CategoryDAO instance = null;
	private static HashMap<String, Category> defaultCategories = new HashMap<>(); //Category name -> Category
	private static HashMap<Long, Category> customAddedCategories = new HashMap<>(); //User id -> Category
	
	private CategoryDAO() throws Exception{
		
		String query = "";
		PreparedStatement stmt = null;
		Connection con = DBManager.getInstance().getConnection();
		
		// get all default categories and add them to income and expense categories
		query = "SELECT category.id as id, category.type_id as type, category.name AS name, default_icon.name AS icon FROM category INNER JOIN default_icon ON default_icon.id = category.icon_id WHERE category.role_id = 1";
		try {
			stmt = con.prepareStatement(query);
			ResultSet rs2 = stmt.executeQuery();
			while(rs2.next()){
				try {
					String categorieName = rs2.getString("name");
					String categorieIcon = rs2.getString("icon");
					long categorieType = rs2.getLong("type");
					long categorieId = rs2.getLong("id");
					Category newCategory = null;
					if(categorieType == 1){
						newCategory = new Category(categorieName, categorieIcon, Category.TYPE.INCOME);
						newCategory.setId(categorieId);
					}
					else{
						newCategory = new Category(categorieName, categorieIcon, Category.TYPE.EXPENSE);
						newCategory.setId(categorieId);
					}
					
					// add to all default categories
					defaultCategories.put(newCategory.getName(), newCategory);
				} catch (InvalidCashFlowException e) {
					System.out.println("CategoryDAO->Default Categories->InvalidCashFlow: " + e.getMessage());
					throw e;
				}
			}
			
			// get all custom user Categories
			query = "SELECT category.id, category.type_id as type, category.name AS name, default_icon.name AS icon, user_category.user_id AS user_id FROM category INNER JOIN default_icon ON category.icon_id = default_icon.id INNER JOIN user_category ON user_category.category_id = category.id";
			stmt = con.prepareStatement(query);
			ResultSet categories = stmt.executeQuery();
			while(categories.next()){
				try {
					String categorieName = categories.getString("name");
					String categorieIcon = categories.getString("icon");
					long categorieType = categories.getLong("type");
					long userId = categories.getLong("user_id");
					long categorieId = categories.getLong("id");
					
					// add to all custom categories
					Category newCategory = null;
					if(categorieType == 1){
						newCategory = new Category(categorieName, categorieIcon, Category.TYPE.INCOME);
						newCategory.setId(categorieId);
					}
					else{
						newCategory = new Category(categorieName, categorieIcon, Category.TYPE.EXPENSE);
						newCategory.setId(categorieId);
					}
					customAddedCategories.put(userId, newCategory);
				} catch (InvalidCashFlowException e) {
					System.out.println("CategoryDAO->Custom categories->InvalidCashFlow: " + e.getMessage());
					throw e;
				}
			}
		} catch (SQLException e) {
			System.out.println("CategoryDAO->Constructor: " + e.getMessage());
			throw e;
		}
	}
		
	public synchronized static CategoryDAO getInstance() throws Exception{
		if(instance == null){
			instance = new CategoryDAO();
		}
		return instance;
	}
	
	public synchronized void addCustomCategory(long id, Category toAdd){
		if(Validator.validCategory(toAdd)){
			customAddedCategories.put(id, toAdd);
			
			// TODO for database
		}
	}
	
	public Map<String, Category> getAllDefaultCategories(){
		return Collections.unmodifiableMap(defaultCategories);
	}
	
	public Map<Long, Category> getAllCustomAddedCategories(){
		return Collections.unmodifiableMap(customAddedCategories);
	}
	
	public ArrayList<Category> getAllUserCategories(long userId){
		ArrayList<Category> result = new ArrayList<>();
		for(Entry<String, Category> i : defaultCategories.entrySet()){
			result.add(i.getValue());
		}
		for(Entry<Long, Category> i : customAddedCategories.entrySet()){
			if(i.getKey() == userId){
				result.add(i.getValue());
			}
		}
		return result;
	}
}
