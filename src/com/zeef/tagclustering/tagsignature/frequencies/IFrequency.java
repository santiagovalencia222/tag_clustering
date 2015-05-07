package com.zeef.tagclustering.tagsignature.frequencies;

public interface IFrequency {

	public Double getFrequency(String term);

	public Double calculateFrequencyWeight(Double frequency);

}
