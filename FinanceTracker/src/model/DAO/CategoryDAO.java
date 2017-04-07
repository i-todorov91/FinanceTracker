package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.budget.flows.Category;
import model.budget.flows.Expense;
import model.budget.flows.Income;
import model.user.User;
import model.util.Validator;
import model.util.exceptions.InvalidCashFlowException;

public class CategoryDAO {
	private static CategoryDAO instance = null;
	private static HashMap<String, Category> defaultCategories = new HashMap<>(); //Category name -> Category
	private static HashMap<String, HashMap<Category, Long>> customAddedCategories = new HashMap<>(); //Category name -> (Category, User id)
	@SuppressWarnings({ "static-access", "unchecked" })
	private CategoryDAO(){
		// TODO add transactions
		String query = "";
		PreparedStatement stmt = null;
		// get all default categories and add them to income and expense categories
		query = "SELECT category.name AS name, default_icon.name AS icon FROM category INNER JOIN default_icon ON default_icon.id = category.icon_id WHERE category.type_id = 1";
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
			stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
			ResultSet rs2 = stmt.executeQuery();
			while(rs2.next()){
				try {
					String categorieName = rs2.getString("name");
					String categorieIcon = rs2.getString("icon");
					Category newCategory = new Category(categorieName, categorieIcon);
					
					// add to all default categories
					defaultCategories.put(newCategory.getName(), newCategory);
				} catch (InvalidCashFlowException e) {
					System.out.println("CategoryDAO->Default Categories->InvalidCashFlow: " + e.getMessage());
				}
			}
			
		// get all custom user Categories
		query = "SELECT category.name AS name, default_icon.name AS icon, user_category.user_id AS user_id FROM category INNER JOIN default_icon ON category.icon_id = default_icon.id INNER JOIN user_category ON user_category.category_id = category.id";
			stmt = DBManager.getInstance().getInstance().getConnection().prepareStatement(query);
			ResultSet categories = stmt.executeQuery();
			while(categories.next()){
				String categorieName = categories.getString("name");
				String categorieIcon = categories.getString("icon");
				long userId = categories.getLong("user_id");
				
				// add to all custom categories
				try {
					customAddedCategories.put(categorieName, (HashMap<Category, Long>) new HashMap<>().put(new Category(categorieName, categorieIcon), userId));
				} catch (InvalidCashFlowException e) {
					System.out.println("CategoryDAO->Custom categories->InvalidCashFlow: " + e.getMessage());
				}
			}
			DBManager.getInstance().getConnection().commit();
		} catch (SQLException e) {
			System.out.println("CategoryDAO->Constructor: " + e.getMessage());
			try {
				DBManager.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				System.out.println("CategoryDAO->Constructor->rollBack: " + e1.getMessage());
			}
		}
		finally{
			try {
				DBManager.getInstance().getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("CategoryDAO->Constructor->setAutoCommit(true): " + e.getMessage());
			}
		}
	}
		
	public synchronized static CategoryDAO getInstance(){
		if(instance == null){
			instance = new CategoryDAO();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void addCustomCategory(long id, Category toAdd){
		if(Validator.validCategory(toAdd)){
			customAddedCategories.put(toAdd.getName(), (HashMap<Category, Long>) new HashMap<>().put(toAdd, id));
			
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
