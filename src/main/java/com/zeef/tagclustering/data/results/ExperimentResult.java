package com.zeef.tagclustering.data.results;

public class ExperimentResult {
	
	private Integer randomAlgorithm;
	private String queryTag;
	private String recommendedTag;
	private Integer relevancy;
	private String participantName;
	private Integer round;
	
	public ExperimentResult(Integer randomAlgorithm, String queryTag,
			String recommendedTag, Integer relevancy, String participantName,
			Integer round) {
		this.randomAlgorithm = randomAlgorithm;
		this.queryTag = queryTag;
		this.recommendedTag = recommendedTag;
		this.relevancy = relevancy;
		this.participantName = participantName;
		this.round = round;
	}

	public Integer getRandomAlgorithm() {
		return randomAlgorithm;
	}
	
	public String getQueryTag() {
		return queryTag;
	}
	
	public String getRecommendedTag() {
		return recommendedTag;
	}
	
	public Integer getRelevancy() {
		return relevancy;
	}
	
	public String getParticipantName() {
		return participantName;
	}
	
	public Integer getRound() {
		return round;
	}
	
}
