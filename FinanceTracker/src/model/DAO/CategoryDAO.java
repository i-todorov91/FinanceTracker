package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.budget.flows.Category;
import model.util.Validator;
import model.util.exceptions.InvalidCashFlowException;

public class CategoryDAO {
	
	private static CategoryDAO instance = null;
	private static HashMap<String, Category> defaultCategories = new HashMap<>(); //Category name -> Category
	private static HashMap<String, HashMap<Category, Long>> customAddedCategories = new HashMap<>(); //Category name -> (Category, User id)
	
	private CategoryDAO(){
		
		String query = "";
		PreparedStatement stmt = null;
		Connection con = DBManager.getInstance().getConnection();
		
		// get all default categories and add them to income and expense categories
		query = "SELECT category.id as id, category.type_id as type, category.name AS name, default_icon.name AS icon FROM category INNER JOIN default_icon ON default_icon.id = category.icon_id WHERE category.role_id = 1";
		try {
			con.setAutoCommit(false);
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
					HashMap<Category, Long> innerMap = new HashMap<>();
					innerMap.put(newCategory, userId);
					customAddedCategories.put(categorieName, innerMap);
				} catch (InvalidCashFlowException e) {
					System.out.println("CategoryDAO->Custom categories->InvalidCashFlow: " + e.getMessage());
				}
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("CategoryDAO->Rollback: " + e.getMessage());
			}
			System.out.println("CategoryDAO->Constructor: " + e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("CategoryDAO->setAutoCommit(true): " + e.getMessage());
			}
		}
	}
		
	public synchronized static CategoryDAO getInstance(){
		if(instance == null){
			instance = new CategoryDAO();
		}
		return instance;
	}
	
	public synchronized void addCustomCategory(long id, Category toAdd){
		if(Validator.validCategory(toAdd)){
			HashMap<Category, Long> innerMap = new HashMap<>();
			innerMap.put(toAdd, id);
			customAddedCategories.put(toAdd.getName(), innerMap);
			
			// TODO for database
		}
	}
	
	public Map<String, Category> getAllDefaultCategories(){
		return Collections.unmodifiableMap(defaultCategories);
	}
	
	public Map<String, HashMap<Category, Long>> getAllCustomAddedCategories(){
		return Collections.unmodifiableMap(customAddedCategories);
	}
}
