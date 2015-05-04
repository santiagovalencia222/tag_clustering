package com.zeef.tagclustering.model;

public class Link {

	private String link;
	private String tag;

	public Link(String link, String tag) {
		this.link = link;
		this.tag = tag;
	}

	public String getLink() {
		return link;
	}

	public String getTag() {
		return tag;
	}
}
