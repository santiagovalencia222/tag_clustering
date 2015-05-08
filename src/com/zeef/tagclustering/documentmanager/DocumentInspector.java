package com.zeef.tagclustering.documentmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class DocumentInspector {

	/**
	 * Generates a list of words excluding stop words.
	 * Implementation decisions:
	 * 		1. Read stop words from file.
	 * 		2. Use RegEx to parse input.
	 * @param document
	 * @return wordsInDocument list of words in the document excluding stop words.
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

	public Set<String> getWordSynonyms(String word) {
		Set<String> synonyms = new HashSet<>();
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://wikisynonyms.ipeirotis.com/api/" + word)
			.header("X-Mashape-Key", "mMerZb5CRdmshgIMA5Or9i3z2kNap1aORpWjsnMIvcmA0xKW5E")
			.header("Accept", "application/json")
			.asJson();
			JSONObject jsonObject = response.getBody().getObject();
			JSONArray jsonArray = jsonObject.getJSONArray("terms");
			for (int i = 0 ; i < jsonArray.length() ; i++) {
				jsonObject = (JSONObject) jsonArray.get(i);
				synonyms.add((String) jsonObject.get("term"));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return synonyms;
	}

}
