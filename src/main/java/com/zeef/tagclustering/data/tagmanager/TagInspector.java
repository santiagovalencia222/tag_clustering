package com.zeef.tagclustering.data.tagmanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zeef.tagclustering.data.DataInspector;
import com.zeef.tagclustering.helper.Helper;
import com.zeef.tagclustering.model.Tag;

public class TagInspector implements DataInspector{

	public Set<Tag> getUserTags(String user) {
		TagRetriever retriever = new TagRetriever();
		ResultSet resultSet = retriever.getUserTagList(user);
		Set<Tag> userTags = new HashSet<Tag>();
		Set<String> stopWords = Helper.getStopWords();
		try {
			while (resultSet.next()) {
				if (resultSet.getString("tag_name") != null /*&&
						resultSet.getString("page_title") != null &&
								resultSet.getString("block_title") != null*/) {

					String tagName = resultSet.getString("tag_name");
					if (!Helper.containsIgnoreCase(tagName, stopWords)) {
						Tag tag = new Tag(tagName);
						userTags.add(tag);
					}

					/*String pageName = Helper.normalizeZEEFPageBlockTitle(resultSet.getString("page_title"));
					if (!Helper.containsIgnoreCase(pageName, stopWords)) {
						Tag page = new Tag(pageName);
						userTags.add(page);
					}

					String blockName = Helper.normalizeZEEFPageBlockTitle(resultSet.getString("block_title"));
					if (!Helper.containsIgnoreCase(blockName, stopWords)) {
						Tag block = new Tag(blockName);
						userTags.add(block);
					}*/
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userTags;
	}
	
	public List<Tag> getFRRecommendations(String queryTag) {
		List<Tag> recommendations = new ArrayList<Tag>();
		TagRetriever retriever = new TagRetriever();
		Tag tag = null;
		Integer count = 0;
		ResultSet rs = retriever.getTopTagsFR(queryTag);
		Set<String> stopWords = Helper.getStopWords();
		try {
			while (rs.next() && count < 10) {
				tag = new Tag(rs.getString("tag"));
				if (!recommendations.contains(tag) && 
						!tag.getName().equals(queryTag) && 
							!Helper.containsIgnoreCase(queryTag, stopWords)) {
					recommendations.add(tag);
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recommendations;
	}

	public List<Tag> getRandomWords(Integer round) {
		List<Tag> randomWords = new ArrayList<Tag>();
    	if (round == 1) {
    		randomWords.add(new Tag("plants"));
        	randomWords.add(new Tag("screen"));
        	randomWords.add(new Tag("glass"));
        	randomWords.add(new Tag("wallet"));
        	randomWords.add(new Tag("music"));
        	randomWords.add(new Tag("guitar"));
        	randomWords.add(new Tag("lake"));
        	randomWords.add(new Tag("castle"));
        	randomWords.add(new Tag("yellow"));
        	randomWords.add(new Tag("antartida"));
    	} else if (round == 2) {
    		randomWords.add(new Tag("socks"));
        	randomWords.add(new Tag("mage"));
        	randomWords.add(new Tag("wish"));
        	randomWords.add(new Tag("point"));
        	randomWords.add(new Tag("shell"));
        	randomWords.add(new Tag("tongue"));
        	randomWords.add(new Tag("place"));
        	randomWords.add(new Tag("home"));
        	randomWords.add(new Tag("bed"));
        	randomWords.add(new Tag("crystal"));
    	} else if (round == 3) {
    		randomWords.add(new Tag("fold"));
        	randomWords.add(new Tag("aloha"));
        	randomWords.add(new Tag("lines"));
        	randomWords.add(new Tag("cola"));
        	randomWords.add(new Tag("yolo"));
        	randomWords.add(new Tag("hard"));
        	randomWords.add(new Tag("tight"));
        	randomWords.add(new Tag("finger"));
        	randomWords.add(new Tag("knew"));
        	randomWords.add(new Tag("knee"));
    	} else if (round == 4) {
    		randomWords.add(new Tag("puppet"));
        	randomWords.add(new Tag("foam"));
        	randomWords.add(new Tag("glass"));
        	randomWords.add(new Tag("bill"));
        	randomWords.add(new Tag("gate"));
        	randomWords.add(new Tag("case"));
        	randomWords.add(new Tag("throne"));
        	randomWords.add(new Tag("cost"));
        	randomWords.add(new Tag("nothing"));
        	randomWords.add(new Tag("work-hard"));
    	} else if (round == 5) {
    		randomWords.add(new Tag("master"));
        	randomWords.add(new Tag("chilli"));
        	randomWords.add(new Tag("glow"));
        	randomWords.add(new Tag("tempting"));
        	randomWords.add(new Tag("folder"));
        	randomWords.add(new Tag("fold"));
        	randomWords.add(new Tag("lake"));
        	randomWords.add(new Tag("void"));
        	randomWords.add(new Tag("colon"));
        	randomWords.add(new Tag("south"));
    	}
    	return randomWords;
	}

}
