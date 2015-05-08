package com.zeef.tagclustering.tagsignature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zeef.tagclustering.htmlparser.HTMLParser;
import com.zeef.tagclustering.linkmanager.LinkInspector;
import com.zeef.tagclustering.tagsignature.frequencies.Frequency;
import com.zeef.tagclustering.tagsignature.frequencies.InverseDocumentFrequencyImpl;
import com.zeef.tagclustering.tagsignature.frequencies.TermFrequencyImpl;

public class TagSignature {

	private Map<String, List<Double>> termFrequencyInverseDocumentFrequencyVector = new HashMap<>();

	public void calculateTFIDF() {
		List<String> docs = new ArrayList<>();
		docs.add("Hello, thIs iS AN example document. ThiS exampLe contains special characters!");
		docs.add("This is another example of a great document for testing example");
		docs.add("This document doesnt have the word");
		docs.add("Nor this,, or the other one that ");
		docs.add("Example again appearing in this document, example in this document, in this document, in this document, in this document, in this document, in this document, in this document, example, example, example, example, example, example, example");
		docs.add("blah this is another document without the word");
		Frequency idf = new InverseDocumentFrequencyImpl(docs);
		Double idfFreq = idf.getFrequency("example");
		Double idfFreqWeight = idf.calculateFrequencyWeight(idfFreq);
		List<Double> tfidfWeights = new ArrayList<>();
		for (String doc : docs) {
			Frequency tf = new TermFrequencyImpl(doc);
			Double tfFreq = tf.getFrequency("example");
			Double tfFreqWeight = tf.calculateFrequencyWeight(tfFreq);
			tfidfWeights.add(tfFreqWeight * idfFreqWeight);
		}
		termFrequencyInverseDocumentFrequencyVector.put("example", tfidfWeights);
	}

	public Map<String, List<Double>> getTermFrequencyInverseDocumentFrequencyVector() {
		return termFrequencyInverseDocumentFrequencyVector;
	}

	public Map<String, List<String>> getTaggedDocuments() {
		Map<String, List<String>> taggedDocuments = new HashMap<>();
		LinkInspector li = new LinkInspector();
		HTMLParser parser = new HTMLParser();
		Map<String, Set<String>> taggedLinks = li.linksGroupedByTag();
		for (Entry<String, Set<String>> entry : taggedLinks.entrySet()) {
			for (String url : entry.getValue()) {

			}
		}
		return null;
	}

}
