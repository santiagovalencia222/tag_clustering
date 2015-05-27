package com.zeef.tagclustering.model;

public class Tag {

	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
	    return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if ((((Tag) o).getName()).equals(getName())) {
			result = true;
		}
		return result;
	}

}
