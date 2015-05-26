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
		return "[" + getName() + "]";
	}

	public Boolean equals(Tag tag) {
		Boolean result = false;
		if (tag.getName().equalsIgnoreCase(getName())) {
			result = true;
		}
		return result;
	}

}
