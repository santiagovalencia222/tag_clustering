package com.zeef.tagclustering.model;

public class Tag {

	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Boolean equals(Tag tag) {
		Boolean result = false;
		if (tag.getName().equals(getName())) {
			result = true;
		}
		return result;
	}

}
