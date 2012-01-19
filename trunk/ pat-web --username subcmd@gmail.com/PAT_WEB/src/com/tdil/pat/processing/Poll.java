package com.tdil.pat.processing;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Poll {

	private static Map<String, Integer> pollData = new ConcurrentHashMap<String, Integer>();
	
	static {
		pollData.put("SOPA", 0);
		pollData.put("PIPA", 0);
	}
	public static void newData(String hashtagString) {
		System.out.println("p+" + hashtagString);
		pollData.put(hashtagString, pollData.get(hashtagString) + 1);
	}
	
}
