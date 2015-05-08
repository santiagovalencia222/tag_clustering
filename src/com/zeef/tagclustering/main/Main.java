package com.zeef.tagclustering.main;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		/*LinksInspector linkInspector = new LinksInspector();
		System.out.println("Tags grouped by Links:\n");
		for (Entry<String, Set<String>> entry : linkInspector.tagsGroupedByLink().entrySet()) {
			System.out.println(entry);
		}
		System.out.println("\n\n---------------------------------\n\nLinks grouped by Tags:\n");
		for (Entry<String, Set<String>> entry : linkInspector.linksGroupedByTag().entrySet()) {
			System.out.println(entry);
		}*/

		/*linkInspector.showHostOccurenceBucket();
		linkInspector.generateCSVHostInPages();*/

		/*HTMLParser htmlParser = new HTMLParser();
		htmlParser.extractMetaKeyWordsFromLinksSet();
		for (Entry<URL, Set<String>> entry : htmlParser.getUrlMetaKeyWords().entrySet()) {
			if (!entry.getValue().isEmpty()){
				System.out.println(entry);
			}
		}
		System.out.println(htmlParser.getUrlMetaKeyWords().size());*/

		/*TagSignature tagSignature = new TagSignature();
		tagSignature.calculateTFIDF();
		for (Entry<String, List<Double>> entry : tagSignature.getTermFrequencyInverseDocumentFrequencyVector().entrySet()) {
			System.out.println(entry);
		}*/

		/*DocumentRetriever dr = new DocumentRetriever();
		ResultSet resultSet = dr.getAllDocuments();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}*/
	}
}