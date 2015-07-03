package com.zeef.tagclustering.htmlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zeef.tagclustering.data.linkmanager.LinkInspector;
import com.zeef.tagclustering.model.Resource;

public class HTMLParser {

	private Map<URL, Set<String>> urlMetaKeyWords = new HashMap<>();

	public Map<URL, Set<String>> getUrlMetaKeyWords() {
		return urlMetaKeyWords;
	}

	public String getBodyTextFromUrl(URL url) {
		Document document = getDocument(url);
		return document.body().text();
	}

	public void extractMetaKeyWordsFromLinksSet() throws SQLException {
		LinkInspector linkInspector = new LinkInspector();
		Set<Resource> linksSet = linkInspector.getPositionedLinks();
		for (Resource link : linksSet) {
			try {
				addMetaKeyWordsToMap(new URL(link.getTargetURL()));
			} catch (MalformedURLException e) {
				linkInspector.addIlegalUrl(link.getTargetURL());
			}
		}
	}

	private void addMetaKeyWordsToMap(URL url) {
		Document document = getDocument(url);
		Set<String> keyWordsSet = new HashSet<>();
		try {
			String keyWords = document.select("meta[name=keywords]").first().attr("content");
			if (keyWords.length() != 0) {
				String[] keys = keyWords.split(",");
				for (String key : keys) {
					keyWordsSet.add(normalizeTag(key));
				}
				urlMetaKeyWords.put(url, keyWordsSet);
			}
		} catch (NullPointerException e) {
			//No actions because the Exception is thrown when the document has no meta keywords tag
		}
	}

	private String normalizeTag(String tag) {
		return tag.replaceAll("\\s", "");
	}

	public Document getDocument(URL url) {
		Document document = null;
		try {
			document = Jsoup.connect(url.toString()).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
