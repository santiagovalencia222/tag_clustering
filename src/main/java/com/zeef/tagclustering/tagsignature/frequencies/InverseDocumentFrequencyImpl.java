package com.zeef.tagclustering.tagsignature.frequencies;

import java.util.List;

public class InverseDocumentFrequencyImpl implements Frequency {

	private List<String> documents;

	public InverseDocumentFrequencyImpl(List<String> documents) {
		this.documents = documents;
	}

	@Override
	public Double getFrequency(String term) {
		Frequency termFrequencyInstance;
		Double inverseDocumentFrequency = 0.0;
		Double termFrequency = 0.0;
		for (String document : documents) {
			termFrequencyInstance = new TermFrequencyImpl(document);
			//TODO Find a way to avoid getting the term frequency again
			termFrequency = termFrequencyInstance.getFrequency(term);
			if (termFrequency > 0) {
				inverseDocumentFrequency++;
			}
		}
		return inverseDocumentFrequency;
	}

	@Override
	public Double calculateFrequencyWeight(Double inverseDocumentFrequency) {
		Double totalDocs = new Double(documents.size());
		Double weight = 0.0;
		if (inverseDocumentFrequency != 0.0) {
			weight = Math.log10(totalDocs/inverseDocumentFrequency);
		}
		return weight;
	}

}
