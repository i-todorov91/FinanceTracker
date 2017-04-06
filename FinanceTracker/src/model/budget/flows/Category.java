package model.budget.flows;

import model.util.Validator;
import model.util.exceptions.InvalidCashFlowException;

public class Category {

	private String name;
	private String icon;
	
	public Category(String name, String icon) throws InvalidCashFlowException {
		if(!Validator.validateString(name) || !Validator.validateString(icon)){
			throw new InvalidCashFlowException();
		}
		this.name = name;
		this.icon = icon;
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
	
		
}
