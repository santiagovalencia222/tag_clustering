package com.zeef.tagclustering.model;

public class Resource {

	private String targetURL;

	public Resource(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getTargetURL() {
		return targetURL;
	}

	@Override
	public String toString() {
		return getTargetURL();
	}

	@Override
	public int hashCode() {
	    return targetURL.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if ((((Resource) o).getTargetURL()).equals(getTargetURL())) {
			result = true;
		}
		return result;
	}

}