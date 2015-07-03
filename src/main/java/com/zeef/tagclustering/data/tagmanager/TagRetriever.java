package com.zeef.tagclustering.data.tagmanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zeef.tagclustering.data.DataRetriever;
import com.zeef.tagclustering.model.Tag;

public class TagRetriever extends DataRetriever{

	public ResultSet getUserTagList(String user){
		return executeSelectQueryZEEF("SELECT DISTINCT link.target_url, tag.name AS tag_name, "
				+ "zeef_user.full_name,	alias.name AS page_title, block.title AS block_title "
				+ "FROM page JOIN subject ON subject.id = page.subject_id "
				+ "JOIN alias ON alias.subject_id = subject.id "
				+ "JOIN block ON block.page_id = page.id "
				+ "JOIN link_position ON link_position.link_block_id = block.id "
				+ "JOIN link ON link.id = link_position.link_id "
				+ "JOIN zeef_user ON page.user_id = zeef_user.id "
				+ "JOIN page_tag ON page.id = page_tag.page_id "
				+ "JOIN tag ON tag.id = page_tag.tag_id WHERE alias.default_alias "
				+ "AND zeef_user.full_name = '" + user + "'");
	}
	
	public ResultSet getTopTagsFR(String queryTag) {
		return executeSelectQueryFR("SELECT DISTINCT(weights.item) AS tag, weights.weight, "
				+ "rankings.item FROM weights JOIN rankings ON weights.id = rankings.id WHERE "
				+ "rankings.item IN (SELECT DISTINCT(tas.uri) FROM tas WHERE tas.tag = '" + queryTag + "') "
				+ "AND weights.itemtype = 0 ORDER BY weights.weight DESC");
	}
	
	public void updateZEEFTCOWeightResults(String queryTag, String tag, Double weight) {
		executeUpdateQuery("UPDATE results SET weight_zeeftco = " + weight + " WHERE "
				+ "query_tag = '" + queryTag + "' AND "
				+ "recommended_tag = '" + tag + "' AND "
				+ "algorithm = 1");
	}
	
	public void updateblah(String t1, String t2, Double w) {
		executeUpdateQuery("UPDATE results set weight_zeeftco = " + w
				+ " where query_tag = '" + t1 + "' and recommended_tag = '" + t2 + "'");
	}
	
	public ResultSet getDistinctQueryTags() {
		return executeSelectQueryLocal("SELECT DISTINCT(query_tag) AS qt FROM results WHERE algorithm = 1 AND query_tag != 'poker' ORDER BY qt");
	}

}
