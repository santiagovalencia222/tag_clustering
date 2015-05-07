package com.zeef.tagclustering.tagsignature.frequencies;

import java.util.List;

public class InverseDocumentFrequencyImpl implements IFrequency {

	private List<String> documents;

	public InverseDocumentFrequencyImpl(List<String> documents) {
		this.documents = documents;
	}

	@Override
	public Double getFrequency(String term) {
		IFrequency termFrequencyInstance;
		Double inverseDocumentFrequency = 0.0;
		for (String document : documents) {
			termFrequencyInstance = new TermFrequencyImpl(document);
			Double termFrequency = termFrequencyInstance.getFrequency(term);
			if (termFrequency > 0) {
				inverseDocumentFrequency++;
			}
		}
		return inverseDocumentFrequency;
	}

	@Override
	public Double calculateFrequencyWeight(Double inverseDocumentFrequency) {
		Double totalDocs = new Double(documents.size());
		return Math.log10(totalDocs/inverseDocumentFrequency);
	}

}
