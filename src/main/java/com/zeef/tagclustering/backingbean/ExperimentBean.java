package com.zeef.tagclustering.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.javatuples.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.data.graphmanager.GraphManager;
import com.zeef.tagclustering.data.results.ExperimentResult;
import com.zeef.tagclustering.data.results.ResultsManager;
import com.zeef.tagclustering.data.tagmanager.TagInspector;
import com.zeef.tagclustering.data.tagmanager.TagRetriever;
import com.zeef.tagclustering.helper.Helper;
import com.zeef.tagclustering.model.Tag;

@SessionScoped
@ManagedBean
public class ExperimentBean implements Serializable {

	private static final long serialVersionUID = -7618722196826479119L;
	
	private String participantName;
    private String queryTag;
    private String recommendedTag;
    private Integer tagCount = 1;
    private Integer round = 1;
    private Integer randomAlgorithm = 0;
    private Integer totalRecommendedTags = 0;
    private Integer relevancy;
	private List<Integer> order = new ArrayList<Integer>();
    
    private Map<Integer, Pair<String, Integer>> recommendedTags = new HashMap<Integer, Pair<String,Integer>>();
    
    private Map<String, String> participantTagList;
  	private Map<String, String> participants;
  	{
  		participants = new HashMap<String,String>();
  		participants.put("", "");
  		participants.put("Robin Eggenkamp", "Robin Eggenkamp");
  		participants.put("Bauke Scholtz", "Bauke Scholtz");
  		participants.put("Arjan Tijms", "Arjan Tijms");
  		participants.put("Jan Beernink", "Jan Beernink");
  		participants.put("Dennis Brouwer", "Dennis Brouwer");
  		participants.put("Arjan Pronk", "Arjan Pronk");
  		participants.put("Marina Astudillo", "Marina Astudillo");
  		participants.put("Rick Boerebach", "Rick Boerebach");
  		participants.put("Klaas Joosten", "Klaas Joosten");
  		participants.put("Olivier Ozinga", "Olivier Ozinga");
  		participants.put("Yana Ledeneva", "Yana Ledeneva");
  		participants.put("Maud Sztern", "Maud Sztern");
  		participants.put("Rob Thorpe", "Rob Thorpe");
  		participants.put("Marina Polovinchuk", "Marina Polovinchuk");
  		participants.put("Mégane Roger", "Mégane Roger");
  		participants.put("Robin Good", "Robin Good");
  		participants.put("Thomas Vander Wal", "Thomas Vander Wal");
  		participants.put("Maria Golovanova", "Maria Golovanova");
  		participants.put("Menno Kolkert", "Menno Kolkert");
  	}

	public Integer getTotalRecommendedTags() {
		return totalRecommendedTags;
	}

	public void setTotalRecommendedTags(Integer totalRecommendedTags) {
		this.totalRecommendedTags = totalRecommendedTags;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getRelevancy() {
		return relevancy;
	}

	public void setRelevancy(Integer relevancy) {
		this.relevancy = relevancy;
	}

	public Integer getTagCount() {
		return tagCount;
	}

	public void setTagCount(Integer tagCount) {
		this.tagCount = tagCount;
	}

	public String getRecommendedTag() {
		return recommendedTag;
	}

	public void setRecommendedTag(String recommendedTag) {
		this.recommendedTag = recommendedTag;
	}

	public String getQueryTag() {
		return queryTag;
	}

	public void setQueryTag(String queryTag) {
		this.queryTag = queryTag;
	}

	public Map<String, String> getParticipants() {
  		return participants;
  	}

    public Map<String, String> getParticipantTagList() {
		return participantTagList;
	}

	public String getParticipantName() {
        return participantName;
    }

	public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

	public void generateParticipantTagList() {
		participantTagList = new HashMap<String, String>();
		try {
			TagInspector inspector = new TagInspector();
			for (Tag tag : inspector.getUserTags(participantName)) {
				participantTagList.put(tag.getName(), tag.getName());
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("usertags.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void getTagRecommendations() {
    	Integer id = 0;

    	executeZTG();
    	/*
    	//Execute ZEEFTagClustering Algorithm. Recommended tags are placed in the recommendedTags map
	    for (Tag tag : executeZTG()) {
	    	recommendedTags.put(id++, new Pair<String, Integer>(tag.getName(), 1));
	    }
	    
    	//Execute FolkRank Algorithm. Recommended tags are placed in the recommendedTags map
	    for (Tag tag : executeFR()) {
	    	recommendedTags.put(id++, new Pair<String, Integer>(tag.getName(), 2));
	    }

		//Execute Random words. Recommended tags are placed in the recommendedTags map
	    for (Tag tag : executeR()) {
	    	recommendedTags.put(id++, new Pair<String, Integer>(tag.getName(), 3));
	    }
    	totalRecommendedTags = recommendedTags.size();*/
    }

	private void executeZTG() {
		GraphManager manager = new GraphManager();
		SimpleWeightedGraph<Tag, DefaultWeightedEdge> undirectedGraph = manager.buildUndirectedGraph();
		TagRetriever r = new TagRetriever();
		r.updateblah("cro", 
				"tools-(that-will-make-you-live-easier)", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("cro"), 
								new Tag("tools-(that-will-make-you-live-easier)"))));
		
		r.updateblah("volvooceanrace", 
				"race-news", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("volvooceanrace"), 
								new Tag("race-news"))));
		
		r.updateblah("volvooceanrace", 
				"abu-dhabi-ocean-racing", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("volvooceanrace"), 
								new Tag("abu-dhabi-ocean-racing"))));
		
		r.updateblah("poker", 
				"online-poker-for-playmoney", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("poker"), 
								new Tag("online-poker-for-playmoney"))));
		
		r.updateblah("jobs", 
				"sites-emploi", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("jobs"), 
								new Tag("sites-emploi"))));
		
		r.updateblah("amsterdam", 
				"iamsterdam", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("amsterdam"), 
								new Tag("iamsterdam"))));
		
		r.updateblah("handmade", 
				"social-media-groups", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("handmade"), 
								new Tag("social-media-groups"))));
		
		r.updateblah("handmade", 
				"instruments", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("handmade"), 
								new Tag("instruments"))));
		
		r.updateblah("java", 
				"webapp", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("java"), 
								new Tag("webapp"))));
		
		r.updateblah("shopping", 
				"mensclothing", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("shopping"), 
								new Tag("mensclothing"))));
		
		r.updateblah("java", 
				"j2ee", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("java"), 
								new Tag("j2ee"))));
		
		r.updateblah("training", 
				"vj-tools", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("training"), 
								new Tag("vj-tools"))));
		
		r.updateblah("bookmarking", 
				"marketing", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("bookmarking"), 
								new Tag("marketing"))));
		
		r.updateblah("search", 
				"promomasters", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("search"), 
								new Tag("promomasters"))));
		
		r.updateblah("amsterdam", 
				"tips", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("amsterdam"), 
								new Tag("tips"))));
		
		r.updateblah("amsterdam", 
				"bitterballen", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("amsterdam"), 
								new Tag("bitterballen"))));
		
		r.updateblah("shopping", 
				"mensclothing", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("shopping"), 
								new Tag("mensclothing"))));
		
		r.updateblah("development", 
				"springframework", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("development"), 
								new Tag("springframework"))));
		
		r.updateblah("electronics", 
				"drones", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("electronics"), 
								new Tag("drones"))));
		
		r.updateblah("creativity", 
				"technology", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("creativity"), 
								new Tag("technology"))));
		
		r.updateblah("poker", 
				"gambling", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("poker"), 
								new Tag("gambling"))));
		
		r.updateblah("handmade", 
				"books-&-magazines", 
				undirectedGraph.getEdgeWeight(
						undirectedGraph.getEdge(
								new Tag("handmade"), 
								new Tag("books-&-magazines"))));
		
		
		/*Map<Tag, List<Pair<Tag, Double>>> mainCoTags = null;
		ResultSet rs = r.getDistinctQueryTags();
		Tag tag = null;
		try {
			while (rs.next()) {
				tag = new Tag(rs.getString("qt"));
				mainCoTags = manager.getMainCoTags(tag);
				System.out.println(tag + " - " + manager.getMainCoTags(tag));
				for (Pair<Tag, Double> pair : mainCoTags.get(tag)) {
					r.updateZEEFTCOWeightResults(tag.getName(), pair.getValue0().getName(), pair.getValue1());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	private List<Tag> executeFR() {
		TagInspector inspector = new TagInspector();
		return inspector.getFRRecommendations(queryTag);
	}
	
	private List<Tag> executeR() {
		List<Tag> recommendedTagsR = new ArrayList<Tag>();
		TagInspector inspector = new TagInspector();
		recommendedTagsR = inspector.getRandomWords(round);
    	return recommendedTagsR;
	}
 
    public void selectRandomRecommendedTag() throws IOException {
		if (tagCount < totalRecommendedTags) {
	    	recommendedTag = recommendedTags.get(order.get(0)).getValue0();
	    	recommendedTag = Helper.normalizeZEEFPageBlockTitle(recommendedTag);
	    	randomAlgorithm = recommendedTags.get(order.get(0)).getValue1();
	    	recommendedTags.remove(order.get(0));
	    	order.remove(0);
    	} else {
    		queryTag = null;
			FacesContext.getCurrentInstance().getExternalContext().redirect("usertags.xhtml");
			generateRandomOrderList();
			tagCount = 0;
			round++;
		}
    }

    public void saveRelevancyToDB() {
    	ResultsManager manager = new ResultsManager();
    	ExperimentResult result = new ExperimentResult(randomAlgorithm, queryTag, recommendedTag, relevancy, participantName, round);
    	manager.saveResults(result);
    }
    
    public void getRecommendationsListener(AjaxBehaviorEvent event) {
    	try {
    		getTagRecommendations();
    		generateRandomOrderList();
    		selectRandomRecommendedTag();
    		participantTagList.remove(queryTag);
			FacesContext.getCurrentInstance().getExternalContext().redirect("recommendations.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void saveTagRelevancyListener(AjaxBehaviorEvent event) throws IOException {
    	if (round == 5 && tagCount >= totalRecommendedTags) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("thanks.xhtml");
		        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else {
        	saveRelevancyToDB();
        	selectRandomRecommendedTag();
        	relevancy = null;
        	tagCount++;
    	}
    }

	private void generateRandomOrderList() {
		order = Helper.getRandomOrder(totalRecommendedTags);
		Collections.shuffle(order);
	}
	
}