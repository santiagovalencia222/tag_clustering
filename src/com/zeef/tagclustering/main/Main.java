package com.zeef.tagclustering.main;

import java.net.MalformedURLException;
import java.sql.SQLException;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.data.graph.manager.GraphManager;

public class Main {

	public static void main(String[] args) throws SQLException, MalformedURLException {
		GraphManager tca = new GraphManager();
		SimpleWeightedGraph<String, DefaultWeightedEdge> undirectedGraph = tca.buildUndirectedGraph();
		System.out.println(tca.getMainCoTags("samsung"));
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