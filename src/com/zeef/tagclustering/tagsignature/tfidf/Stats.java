package com.zeef.tagclustering.tagsignature.tfidf;

public class Stats {

	private String term;
	private Double termFrequency;
	private Double inverseDocumentFrequency;
	private Double weight;

	public Stats(String term, Double termFrequency, Double inverseDocumentFrequency, Double weight) {
		this.term = term;
		this.termFrequency = termFrequency;
		this.inverseDocumentFrequency = inverseDocumentFrequency;
		this.weight = weight;
	}

	public String getTerm() {
		return term;
	}

	public Double getTermFrequency() {
		return termFrequency;
	}

	public Double getInverseDocumentFrequency() {
		return inverseDocumentFrequency;
	}

	public Double getWeight() {
		return weight;
	}

}
