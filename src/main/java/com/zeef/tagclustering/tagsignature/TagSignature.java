package com.zeef.tagclustering.tagsignature;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zeef.tagclustering.data.documentmanager.DocumentInspector;
import com.zeef.tagclustering.model.Tag;
import com.zeef.tagclustering.tagsignature.frequencies.Frequency;
import com.zeef.tagclustering.tagsignature.frequencies.InverseDocumentFrequencyImpl;
import com.zeef.tagclustering.tagsignature.frequencies.TermFrequencyImpl;

public class TagSignature {

	private Map<String, List<Double>> termFrequencyInverseDocumentFrequencyMatrix = new HashMap<>();

	public void calculateTFIDF() throws MalformedURLException {
		DocumentInspector inspector = new DocumentInspector();
		for (Entry<Tag, List<String>> entry : inspector.getTaggedDocuments().entrySet()) {
			List<String> docs = entry.getValue();
			String tag = entry.getKey().getName();
			Frequency idf = new InverseDocumentFrequencyImpl(docs);
			Double idfFreq = idf.getFrequency(tag);
			Double idfFreqWeight = idf.calculateFrequencyWeight(idfFreq);
			List<Double> tfidfWeights = new ArrayList<>();
			for (String doc : docs) {
				Frequency tf = new TermFrequencyImpl(doc);
				Double tfFreq = tf.getFrequency(tag);
				Double tfFreqWeight = tf.calculateFrequencyWeight(tfFreq);
				tfidfWeights.add(tfFreqWeight * idfFreqWeight);
			}
			Collections.sort(tfidfWeights, Collections.reverseOrder());
			termFrequencyInverseDocumentFrequencyMatrix.put(tag, tfidfWeights);
		}
	}

	public Map<String, List<Double>> getTermFrequencyInverseDocumentFrequencyMatrix() {
		return termFrequencyInverseDocumentFrequencyMatrix;
	}

}
