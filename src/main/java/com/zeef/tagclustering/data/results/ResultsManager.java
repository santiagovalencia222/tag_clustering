package com.zeef.tagclustering.data.results;

import com.zeef.tagclustering.db.DBConnection;

public class ResultsManager {
	
	public void saveResults(ExperimentResult result) {
		DBConnection connection = new DBConnection();
		connection.doLocalConnection();
		connection.executeUpdate("INSERT INTO results (algorithm, query_tag, recommended_tag, "
    			+ "relevancy, participant, datetime, round) VALUES ('" + result.getRandomAlgorithm() + "', '"
    			+ result.getQueryTag() + "', '" + result.getRecommendedTag() + "' , " 
    			+ result.getRelevancy() + ", '" + result.getParticipantName() + "', NOW(), " 
    			+ result.getRound() + ")");
	}

}
