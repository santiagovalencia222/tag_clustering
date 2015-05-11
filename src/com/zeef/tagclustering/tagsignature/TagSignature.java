package com.zeef.tagclustering.tagsignature;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zeef.tagclustering.documentmanager.DocumentInspector;
import com.zeef.tagclustering.tagsignature.frequencies.Frequency;
import com.zeef.tagclustering.tagsignature.frequencies.InverseDocumentFrequencyImpl;
import com.zeef.tagclustering.tagsignature.frequencies.TermFrequencyImpl;

public class TagSignature {

	private Map<String, List<Double>> termFrequencyInverseDocumentFrequencyVector = new HashMap<>();

	public void calculateTFIDF() throws MalformedURLException {
		DocumentInspector inspector = new DocumentInspector();
		for (Entry<String, List<String>> entry : inspector.getTaggedDocuments().entrySet()) {
			List<String> docs = entry.getValue();
			Frequency idf = new InverseDocumentFrequencyImpl(docs);
			Double idfFreq = idf.getFrequency(entry.getKey());
			Double idfFreqWeight = idf.calculateFrequencyWeight(idfFreq);
			List<Double> tfidfWeights = new ArrayList<>();
			for (String doc : docs) {
				Frequency tf = new TermFrequencyImpl(doc);
				Double tfFreq = tf.getFrequency(entry.getKey());
				Double tfFreqWeight = tf.calculateFrequencyWeight(tfFreq);
				tfidfWeights.add(tfFreqWeight * idfFreqWeight);
			}
			termFrequencyInverseDocumentFrequencyVector.put(entry.getKey(), tfidfWeights);
		}
	}

	public Map<String, List<Double>> getTermFrequencyInverseDocumentFrequencyVector() {
		return termFrequencyInverseDocumentFrequencyVector;
	}

}
