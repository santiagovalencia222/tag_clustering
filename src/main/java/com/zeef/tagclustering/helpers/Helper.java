package com.zeef.tagclustering.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.sound.sampled.ReverbType;

public class Helper {

	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
	        @Override
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ) {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list) {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}

	public static Set<String> getStopWords() {
		Set<String> stopWords = new HashSet<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("stopWords.txt")));
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

	public static Boolean containsIgnoreCase(String word, Collection<String> setOfWords) {
		Boolean result = false;
		for (String wordInSet : setOfWords) {
			if (word.equalsIgnoreCase(wordInSet) && !result) {
				result = true;
			}
		}
		return result;
	}

	public static String normalizeWord(String word) {
		return word.replaceAll("\\s", "");
	}

	public static String normalizeZEEFPageBlockTitle(String blockTitle) {
		return blockTitle.toLowerCase().replaceAll("\\s", "-");
	}
    
    public static List<Integer> getRandomOrder(Integer max) {
    	List<Integer> order = new ArrayList<Integer>();
    	for (Integer min = 0 ; min < max ; min++) {
    		order.add(min);
    	}
    	return order;
    }
}
