package com.zeef.tagclustering.main;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.data.graph.manager.GraphManager;
import com.zeef.tagclustering.gui.graphvisualizer.GraphVisualizer;
import com.zeef.tagclustering.model.Tag;

public class Main {

	public static void main(String[] args) {

		//Generate a graph of co-tags
		System.out.println("1. Generating Undirected Weighted Tag Graph...");
		GraphManager manager = new GraphManager();
		SimpleWeightedGraph<Tag, DefaultWeightedEdge> graph = manager.buildUndirectedGraph();
		System.out.println("Done!\n");

		//Drawing the graph
		System.out.println("2. Drawing the graph...");
		GraphVisualizer gv = new GraphVisualizer(graph);
		gv.init();
		System.out.println("Done!");

		//10 Most relevant tags based on a given key word
		Tag tag = new Tag("sport");
		System.out.println("3. The following are the 10 (or less) most relevant tags related with tag " + tag);
		System.out.println(manager.getMainCoTags(tag));
		System.out.println("Done!\n");


		//Generate the input data from DB to construct the Folksonomy
		/*System.out.println("1. Constructing the folksonomy...");
		InputData input = new InputData();
		System.out.println("\t1.1. Generating the input data...");
		Set<Triplet<List<Tag>, Resource, User>> folksonomy = input.generateInputData();
		System.out.println("\tDone!\n");

		//Populate 2D & 3D tensors with the input data previously generated
		System.out.println("\t1.2. Populating 2D & 3D tensors...");
		input.populate2DTensor(folksonomy);
		System.out.println("\t\t1.2.1. 2D tensor populated!");
		input.populate3DTensor(folksonomy);
		System.out.println("\t\t1.2.2. 3D tensor populated!");
		System.out.println("\tDone!\n");

		//System.out.println(undirectedGraph.toString());
		//System.out.println(undirectedGraph.getAllEdges(new Tag("SEO"), new Tag("content-marketing")));
		//System.out.println(undirectedGraph.getEdge("SEO", "content-marketing"));
		//System.out.println(undirectedGraph.getEdgeWeight(undirectedGraph.getEdge("SEO", "content-marketing")));
		/*for(Pair<Tag, Tag> pair : tca.generateCoTags()) {
			System.out.println("<" + pair.getValue0().getName() + ", " + pair.getValue1().getName() + ">");
		}
		for(Entry<Pair<Tag, Tag>, Integer> entry : tca.getCoTagOccurences().entrySet()) {
			System.out.println("<" + entry.getKey().getValue0().getName() + ", " + entry.getKey().getValue1().getName() + ">, " + entry.getValue());
		}*/

		/*InputData inputData = new InputData();
		inputData.generateInputData();
		Map<Triplet<Tag, Resource, User>, Boolean> tensor = inputData.getTensor();
		for (Entry<Tuple<String, String, String>, Boolean> entry : tensor.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println("\n");
		}*/

		/*System.out.println("Tags grouped by Links:\n");
		for (Entry<String, Set<String>> entry : linkInspector.tagsGroupedByLink().entrySet()) {
			System.out.println(entry);
		}*/
		/*Set<Bookmark> bookmarks = linkInspector.getBookmarks();
		for (Bookmark bookmark : bookmarks) {
			System.out.println("Link: " + bookmark.getUrl());
			System.out.println("User: " + bookmark.getUser());
			System.out.println("Tags: " + bookmark.getTags() + "\n");
		}*/

		/*DocumentInspector di = new DocumentInspector();
		for (String synonym : di.getWordWikiSynonyms("iphone")) {
			System.out.println(synonym);
		}*/

		/*linkInspector.showHostOccurenceBucket();
		linkInspector.generateCSVHostInPages();*/

		/*HTMLParser htmlParser = new HTMLParser();
		htmlParser.extractMetaKeyWordsFromLinksSet();
		for (Entry<URL, Set<String>> entry : htmlParser.getUrlMetaKeyWords().entrySet()) {
			if (!entry.getValue().isEmpty()){
				System.out.println(entry);
			}
		}
		System.out.println(htmlParser.getUrlMetaKeyWords().size());*/

		/*TagSignature tagSignature = new TagSignature();
		tagSignature.calculateTFIDF();
		for (Entry<String, List<Double>> entry : tagSignature.getTermFrequencyInverseDocumentFrequencyMatrix().entrySet()) {
			System.out.println(entry);
		}*/

		/*DocumentRetriever dr = new DocumentRetriever();
		ResultSet resultSet = dr.getAllDocuments();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}*/
	}
}