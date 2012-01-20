package com.tdil.pat.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Poll {

	private static Map<String, AtomicInteger> pollData = new ConcurrentHashMap<String, AtomicInteger>();
	
	public static void newData(String hashtagString) {
		if (!pollData.containsKey(hashtagString)) {
			pollData.put(hashtagString, new AtomicInteger(1));
		} else {
			pollData.get(hashtagString).incrementAndGet();
		}
	}
	
	public static void reset() {
		pollData = new ConcurrentHashMap<String, AtomicInteger>();
	} 
	
	public static Map<String, AtomicInteger> getPollData() {
		Map<String, AtomicInteger> result = new HashMap<String, AtomicInteger>();
		result.putAll(pollData);
		return result;
	}
	
}
