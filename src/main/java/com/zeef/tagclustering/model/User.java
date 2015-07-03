package com.zeef.tagclustering.model;

public class User {

	private String fullName;

	public User(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	@Override
	public String toString() {
		return getFullName();
	}

	@Override
	public int hashCode() {
	    return fullName.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if ((((User) o).getFullName()).equals(getFullName())) {
			result = true;
		}
		return result;
	}

}
