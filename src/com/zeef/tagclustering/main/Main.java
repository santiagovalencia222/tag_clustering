package com.zeef.tagclustering.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zeef.tagclustering.tagsignature.frequencies.IFrequency;
import com.zeef.tagclustering.tagsignature.frequencies.InverseDocumentFrequencyImpl;
import com.zeef.tagclustering.tagsignature.frequencies.TermFrequencyImpl;

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

		List<String> docs = new ArrayList<>();
		docs.add("Hello, thIs iS AN example document. ThiS exampLe contains special characters!");
		docs.add("This is another example of a great document for testing");
		docs.add("This document doesnt have the word");
		docs.add("Nor this,, or the other one that");
		docs.add("Example again appearing in this document, example in this document, in this document, in this document, in this document, in this document, in this document, in this document, example, example, example, example, example, example, example");
		docs.add("blah this is another document without the word");
		IFrequency idf = new InverseDocumentFrequencyImpl(docs);
		Double idfFreq = idf.getFrequency("example");
		for (String doc : docs) {
			IFrequency tf = new TermFrequencyImpl(doc);
			Double tfFreq = tf.getFrequency("example");
			System.out.println("Document: " + doc);
			System.out.println("Term frequency of the word example: " + tfFreq);
			System.out.println("Term frequency weigth of the word example: " +
								tf.calculateFrequencyWeight(tfFreq));
			System.out.println("\n");
		}
		System.out.println("Inverse Document frequency of the word example: " + idfFreq);
		System.out.println("Inverse Document frequency weigth of the word example: " +
							idf.calculateFrequencyWeight(idfFreq));
	}
}