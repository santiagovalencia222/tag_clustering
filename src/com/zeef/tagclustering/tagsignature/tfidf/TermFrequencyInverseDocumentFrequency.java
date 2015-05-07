package com.zeef.tagclustering.tagsignature.tfidf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermFrequencyInverseDocumentFrequency {

	private Map<String, Stats> statsBucket = new HashMap<>();

	public Double termFrequency(String term, String document) {
		Double termFrequency = 0.0;
		List<String> wordsInDocument = getWordsInDocument(document);
		for (String wordInDocument : wordsInDocument) {
			if (term.equalsIgnoreCase(wordInDocument)) {
				termFrequency++;
			}
		}
		return termFrequency;
	}

	public Double termFrequencyWeight(Double termFrequency) {
		Double weight = 0.0;
		if (termFrequency != 0.0) {
			weight = 1.0 + Math.log10(termFrequency);
		}
		return weight;
	}

	public Double inverseDocumentFrequency(String term, List<String> documents) {
		Double inverseDocumentFrequency = 0.0;
		for (String document : documents) {
			Double termFrequency = termFrequency(term, document);
			if (termFrequency > 0) {
				inverseDocumentFrequency++;
			}
		}
		return inverseDocumentFrequency;
	}

	public Double inverseDocumentFrequencyWeight(Double inverseDocumentFrequency, Double totalDocs) {
		return Math.log10(totalDocs/inverseDocumentFrequency);
	}

	/**
	 * Generates a list of words excluding stop words.
	 * Implementation decisions:
	 * 		1. Read stop words from file.
	 * 		2. Use RegEx to parse input.
	 * 		2. Store tf in a map. (put it on the method that populates the map)
	 * @param document
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<String> getWordsInDocument(String document) {
		List<String> wordsInDocument = new ArrayList<>();
		Set<String> stopWords = getStopWords();
		Matcher matcher = Pattern.compile("[a-zA-Z]+").matcher(document);
		while (matcher.find()) {
			String word = matcher.group();
			if(!containsIgnoreCase(word, stopWords)) {
				wordsInDocument.add(word);
			}
		}
		return wordsInDocument;
	}

	public Set<String> getStopWords() {
		BufferedReader reader = null;
		Set<String> stopWords = new HashSet<>();
		try {
			reader = new BufferedReader(new FileReader("stopWords.txt"));
			String word;
			while ((word = reader.readLine()) != null) {
				stopWords.add(word);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopWords;
	}

	private Boolean containsIgnoreCase(String word, Collection<String> setOfWords) {
		Boolean result = false;
		for (String wordInSet : setOfWords) {
			if (word.equalsIgnoreCase(wordInSet) && !result) {
				result = true;
			}
		}
		return result;
	}

}
