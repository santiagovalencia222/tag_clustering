package com.zeef.tagclustering.linkmanager;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zeef.tagclustering.model.Bookmark;
import com.zeef.tagclustering.model.Resource;
import com.zeef.tagclustering.model.Tag;

public class LinkInspector {

	private List<Resource> ilegalLinks = new ArrayList<>();
	private LinkRetriever retriever = new LinkRetriever();

	public List<Resource> getIlegalLinks() {
		return ilegalLinks;
	}

	private Integer getLinksTotal() {
		Integer totalLinks = 0;
		Map<Resource, Integer> hostBucket = new HashMap<>();
		try {
			hostBucket = hostOccurrences();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Entry<Resource, Integer> link : hostBucket.entrySet()) {
			totalLinks += link.getValue();
		}
		return totalLinks;
	}

	private Map<Resource, Integer> hostOccurrences() throws SQLException {
		ResultSet resultSet = retriever.getAllLinks();
		URL url = null;
		Integer hostCount = null;
		Map<Resource, Integer> hostBucket = new HashMap<>();
		while (resultSet.next()) {
			try {
				url = new URL(resultSet.getString("target_url"));
				Resource resource = new Resource(url.getHost());
				hostCount = hostBucket.get(resource);
				hostBucket.put(resource, (hostCount == null) ? 1 : hostCount + 1);
			} catch (MalformedURLException e) {
				addIlegalUrl(resultSet.getString("target_url"));
			}
		}
		return hostBucket;
	}

	private Map<Resource, Set<Integer>> getHostsInPages() throws SQLException {
		ResultSet resultSet = retriever.getLinksXPages();
		URL url = null;
		Map<Resource, Set<Integer>> hostInPagesBucket = new HashMap<>();
		while (resultSet.next()) {
			try {
				Integer pageId = Integer.parseInt(resultSet.getString("page_id"));
				url = new URL(resultSet.getString("target_url"));
				Resource resource = new Resource(url.getHost());
				if (hostInPagesBucket.get(url.getHost()) != null) {
					hostInPagesBucket.get(resource).add(pageId);
				}
				else {
					Set<Integer> pagesSet = new HashSet<>();
					pagesSet.add(pageId);
					hostInPagesBucket.put(resource, pagesSet);
				}
			} catch (MalformedURLException e) {
				addIlegalUrl(resultSet.getString("target_url"));
			}
		}
		return hostInPagesBucket;
	}

	public Set<Resource> getPositionedLinks() throws SQLException {
		ResultSet resultSet = retriever.getPositionedLinks();
		Set<Resource> linksSet = new HashSet<>();
		while (resultSet.next()) {
			linksSet.add(new Resource(resultSet.getString("target_url")));
		}
		return linksSet;
	}

	public Map<Resource, Tag> getTaggedLinks() {
		ResultSet resultSet = retriever.getTaggedLinks();
		Map<Resource, Tag> resultData = new HashMap<>();
		try {
			while (resultSet.next()) {
				resultData.put(new Resource(resultSet.getString("target_url")), new Tag(resultSet.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultData;
	}

	public Map<Resource, Set<Tag>> tagsGroupedByLink() {
		Map<Resource, Set<Tag>> taggedLinksBucket = new HashMap<>();
		for (Entry<Resource, Tag> entry : getTaggedLinks().entrySet()) {
			Resource url = entry.getKey();
			Tag tag = entry.getValue();
			if (taggedLinksBucket.get(url) != null) {
				taggedLinksBucket.get(url).add(tag);
			}
			else {
				Set<Tag> tagSet = new HashSet<>();
				tagSet.add(tag);
				taggedLinksBucket.put(url, tagSet);
			}
		}
		return taggedLinksBucket;
	}

	public Map<Tag, Set<Resource>> linksGroupedByTag() {
		Map<Tag, Set<Resource>> taggedLinksBucket = new HashMap<>();
		for (Entry<Resource, Tag> entry : getTaggedLinks().entrySet()) {
			Resource url = entry.getKey();
			Tag tag = entry.getValue();
			if (taggedLinksBucket.get(tag) != null) {
				taggedLinksBucket.get(tag).add(url);
			}
			else {
				Set<Resource> urlSet = new HashSet<>();
				urlSet.add(url);
				taggedLinksBucket.put(tag, urlSet);
			}
		}
		return taggedLinksBucket;
	}

	public Set<Bookmark> getBookmarks() {
		Set<Bookmark> bookmarks = new HashSet<>();
		ResultSet resultSet = retriever.getTaggedLinks();
		List<String> tags;
		try {
			while (resultSet.next()) {
				tags = new ArrayList<>();
				tags.add(resultSet.getString("tag_name"));
				tags.add(resultSet.getString("page_title"));
				tags.add(resultSet.getString("block_title"));
				bookmarks.add(new Bookmark(resultSet.getString("full_name"),
						new URL(resultSet.getString("target_url")), tags));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return bookmarks;
	}

	public void generateCSVHostInPages() {
		Map<Resource, Set<Integer>> hostInPagesBucket = new HashMap<>();
		FileWriter writer = null;
		try {
			hostInPagesBucket = getHostsInPages();
			writer = new FileWriter("/Users/santiagovalenciavargas/Desktop/file.csv");
			for (Entry<Resource, Set<Integer>> hostInPage : hostInPagesBucket.entrySet()) {
				writer.append(hostInPage.getKey().getTargetURL() + ";" + hostInPage.getValue() + ";" + hostInPage.getValue().size() + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHostOccurenceBucket() {
		Map<Resource, Integer> hostBucket = new HashMap<>();
		try {
			hostBucket = hostOccurrences();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Entry<Resource, Integer> host : hostBucket.entrySet()) {
			System.out.println("Host: " + host.getKey().getTargetURL() + "| Occurrence: " + host.getValue());
		}
		System.out.println("Total links: " + getLinksTotal());
		System.out.println("Total ilegal links: " + getIlegalLinks().size());
		System.out.println("Total hosts: " + hostBucket.size());
	}

	public void addIlegalUrl(String url) {
		ilegalLinks.add(new Resource(url));
	}
}
