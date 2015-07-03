package com.zeef.tagclustering.model;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 6579297301511755652L;
	
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
