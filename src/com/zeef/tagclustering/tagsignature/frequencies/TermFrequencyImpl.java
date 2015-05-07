package com.zeef.tagclustering.tagsignature.frequencies;

import java.util.List;

import com.zeef.tagclustering.tagsignature.document.DocumentManager;

public class TermFrequencyImpl implements IFrequency {

	private String document;

	public TermFrequencyImpl(String document) {
		this.document = document;
	}

	@Override
	public Double getFrequency(String term) {
		DocumentManager documentManager = new DocumentManager();
		Double termFrequency = 0.0;
		List<String> wordsInDocument = documentManager.getWordsInDocument(document);
		for (String wordInDocument : wordsInDocument) {
			if (term.equalsIgnoreCase(wordInDocument)) {
				termFrequency++;
			}
		}
		return termFrequency;
	}

	@Override
	public Double calculateFrequencyWeight(Double termFrequency) {
		Double weight = 0.0;
		if (termFrequency != 0.0) {
			weight = 1.0 + Math.log10(termFrequency);
		}
		return weight;
	}

}
