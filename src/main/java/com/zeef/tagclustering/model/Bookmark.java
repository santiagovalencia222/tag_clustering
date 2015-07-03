package com.zeef.tagclustering.model;

import java.net.URL;
import java.util.List;

public class Bookmark {

	private String user;
	private URL url;
	private List<String> tags;

	public Bookmark(String user, URL url, List<String> tags) {
		this.user = user;
		this.url = url;
		this.tags = tags;
	}

	public String getUser() {
		return user;
	}

	public URL getUrl() {
		return url;
	}

	public List<String> getTags() {
		return tags;
	}

}
