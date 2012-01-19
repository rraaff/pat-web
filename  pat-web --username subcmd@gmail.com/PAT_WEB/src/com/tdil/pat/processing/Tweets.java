package com.tdil.pat.processing;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Tweets {

	private static int index = 0;
	private static Status tweets[] = new Status[100];
	
	private static Object mutex = new Object();

	public static void add(Status status, String hashtag) {
		System.out.println("t+" + hashtag);
		synchronized (mutex) {
			tweets[index] = status;
			index = (index + 1) % 100;
		}
	}

	public List<Status> last() {
		Status tweetsCopy[] = new Status[100];
		int currIndex = 0;
		synchronized (mutex) {
			currIndex = index - 1;
			System.arraycopy(tweets, 0, tweetsCopy, 0, 100);
		}
		List<Status> result = new ArrayList<Status>();
		int resultSize = 0;
		int maxResultSize = 10;
		currIndex = currIndex < 0 ? 100 : currIndex;
		while (resultSize < maxResultSize && tweetsCopy[currIndex] != null) {
			result.add(tweetsCopy[currIndex]);
			resultSize = resultSize + 1;
			currIndex = currIndex - 1 < 0 ? 100 : currIndex - 1;
		}
		return result;
	}

}
