package com.ft.model.budget.flows;

import com.ft.model.util.Validator;
import com.ft.model.util.exceptions.InvalidCashFlowException;

public class Category {

	@Override
	public String toString() {
		return "Category [name=" + name + ", icon=" + icon + ", type=" + type + ", id=" + id + "]";
	}

	public static enum TYPE { INCOME, EXPENSE };
	private String name;
	private String icon;
	private TYPE type;
	private long id;
	
	public Category(String name, String icon, TYPE type) throws InvalidCashFlowException {
		if(!Validator.validateString(name) || !Validator.validateString(icon)){
			throw new InvalidCashFlowException();
		}
		this.name = name;
		this.icon = icon;
		this.type = type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getIcon(){
		return this.icon;
	}
	
	public TYPE getType(){
		return this.type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
		
}
