package com.zeef.tagclustering.helpers;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class MapHelper {

	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
	     Map<K,V> result = new LinkedHashMap<>();
	     Stream <Entry<K,V>> st = map.entrySet().stream();
	     st.sorted(Comparator.comparing(e -> ((Entry<K, V>) e).getValue()).reversed())
	          .forEach(e -> result.put(e.getKey(),e.getValue()));
	     return result;
	}
}
