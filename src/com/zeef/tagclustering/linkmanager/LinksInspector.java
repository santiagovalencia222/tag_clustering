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

import com.zeef.tagclustering.model.Link;

public class LinksInspector {

	private List<String> ilegalLinks = new ArrayList<>();
	private LinksRetriever retriever = new LinksRetriever();

	public List<String> getIlegalLinks() {
		return ilegalLinks;
	}

	private Integer getLinksTotal() {
		Integer totalLinks = 0;
		Map<String, Integer> hostBucket = new HashMap<>();
		try {
			hostBucket = hostOccurrences();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Entry<String, Integer> link : hostBucket.entrySet()) {
			totalLinks += link.getValue();
		}
		return totalLinks;
	}

	private Map<String, Integer> hostOccurrences() throws SQLException {
		ResultSet resultSet = retriever.getAllLinks();
		URL url = null;
		Integer hostCount = null;
		Map<String, Integer> hostBucket = new HashMap<>();
		while (resultSet.next()) {
			try {
				url = new URL(resultSet.getString("target_url"));
				hostCount = hostBucket.get(url.getHost());
				hostBucket.put(url.getHost(), (hostCount == null) ? 1 : hostCount + 1);
			} catch (MalformedURLException e) {
				addIlegalUrl(resultSet.getString("target_url"));
			}
		}
		return hostBucket;
	}

	private Map<String, Set<Integer>> getHostsInPages() throws SQLException {
		ResultSet resultSet = retriever.getLinksXPages();
		URL url = null;
		Map<String, Set<Integer>> hostInPagesBucket = new HashMap<>();
		while (resultSet.next()) {
			try {
				Integer pageId = Integer.parseInt(resultSet.getString("page_id"));
				url = new URL(resultSet.getString("target_url"));
				if (hostInPagesBucket.get(url.getHost()) != null) {
					hostInPagesBucket.get(url.getHost()).add(pageId);
				}
				else {
					Set<Integer> pagesSet = new HashSet<>();
					pagesSet.add(pageId);
					hostInPagesBucket.put(url.getHost(), pagesSet);
				}
			} catch (MalformedURLException e) {
				addIlegalUrl(resultSet.getString("target_url"));
			}
		}
		return hostInPagesBucket;
	}

	public Set<String> getPositionedLinks() throws SQLException {
		ResultSet resultSet = retriever.getPositionedLinks();
		Set<String> linksSet = new HashSet<>();
		while (resultSet.next()) {
			linksSet.add(resultSet.getString("target_url"));
		}
		return linksSet;
	}

	public Map<Integer, Link> getTaggedLinks() {
		ResultSet resultSet = retriever.getTaggedLinks();
		Map<Integer, Link> resultData = new HashMap<>();
		Integer id = 0;
		try {
			while (resultSet.next()) {
				resultData.put(id++, new Link(resultSet.getString("target_url"), resultSet.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultData;
	}

	public Map<String, Set<String>> tagsGroupedByLink() {
		Map<String, Set<String>> taggedLinksBucket = new HashMap<>();
		for (Entry<Integer, Link> entry : getTaggedLinks().entrySet()) {
			String url = entry.getValue().getLink();
			String tag = entry.getValue().getTag();
			if (taggedLinksBucket.get(url) != null) {
				taggedLinksBucket.get(url).add(tag);
			}
			else {
				Set<String> tagSet = new HashSet<>();
				tagSet.add(tag);
				taggedLinksBucket.put(url, tagSet);
			}
		}
		return taggedLinksBucket;
	}

	public Map<String, Set<String>> linksGroupedByTag() {
		Map<String, Set<String>> taggedLinksBucket = new HashMap<>();
		for (Entry<Integer, Link> entry : getTaggedLinks().entrySet()) {
			String url = entry.getValue().getLink();
			String tag = entry.getValue().getTag();
			if (taggedLinksBucket.get(tag) != null) {
				taggedLinksBucket.get(tag).add(url);
			}
			else {
				Set<String> urlSet = new HashSet<>();
				urlSet.add(url);
				taggedLinksBucket.put(tag, urlSet);
			}
		}
		return taggedLinksBucket;
	}

	public void generateCSVHostInPages() {
		Map<String, Set<Integer>> hostInPagesBucket = new HashMap<>();
		FileWriter writer = null;
		try {
			hostInPagesBucket = getHostsInPages();
			writer = new FileWriter("/Users/santiagovalenciavargas/Desktop/file.csv");
			for (Entry<String, Set<Integer>> hostInPage : hostInPagesBucket.entrySet()) {
				writer.append(hostInPage.getKey() + ";" + hostInPage.getValue() + ";" + hostInPage.getValue().size() + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHostOccurenceBucket() {
		Map<String, Integer> hostBucket = new HashMap<>();
		try {
			hostBucket = hostOccurrences();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Entry<String, Integer> host : hostBucket.entrySet()) {
			System.out.println("Host: " + host.getKey() + "| Occurrence: " + host.getValue());
		}
		System.out.println("Total links: " + getLinksTotal());
		System.out.println("Total ilegal links: " + getIlegalLinks().size());
		System.out.println("Total hosts: " + hostBucket.size());
	}

	public void addIlegalUrl(String url) {
		ilegalLinks.add(url);
	}
}
