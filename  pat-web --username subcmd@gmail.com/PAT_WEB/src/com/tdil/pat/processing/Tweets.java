package com.tdil.pat.processing;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Tweets {

	private static int index = 0;
	private static Status tweets[] = new Status[100];
	
	private static Object mutex = new Object();

	public static void add(Status status, String hashtag) {
		//System.out.println("t+" + hashtag);
		synchronized (mutex) {
//			System.out.println("Adding " + index);
			tweets[index] = status;
			index = (index + 1) % 100;
		}
	}

	public static List<Status> last(int maxResultSize) {
		Status tweetsCopy[] = new Status[100];
		int currIndex = 0;
		synchronized (mutex) {
			// copio el original
			System.arraycopy(tweets, 0, tweetsCopy, 0, 100);
			currIndex = index - 1;
			currIndex = currIndex < 0 ? 99 : currIndex;
			tweets[currIndex] = null; // CORTE
//			System.out.println("Nulling " + currIndex);
		}
		List<Status> result = new ArrayList<Status>();
		int resultSize = 0;
		while (resultSize < maxResultSize && tweetsCopy[currIndex] != null) {
			result.add(tweetsCopy[currIndex]);
			resultSize = resultSize + 1;
			currIndex = currIndex - 1 < 0 ? 99 : currIndex - 1;
		}
		return result;
	}

}
