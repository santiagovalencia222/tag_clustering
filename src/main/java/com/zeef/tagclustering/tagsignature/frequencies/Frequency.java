package com.zeef.tagclustering.tagsignature.frequencies;

public interface Frequency {

	public Double getFrequency(String term);

	public Double calculateFrequencyWeight(Double frequency);

}
