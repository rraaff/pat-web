package com.tdil.pat.processing;

import java.util.HashSet;
import java.util.Set;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.internal.json.StatusJSONImpl;

public class DataSeparator extends Thread {

	private String hashtagForTweet = "BieberFacts";
	private Set<String> hashtagsForPoll = new HashSet<String>();
	
	private static long sleepWhenNoData = 10; // 10 millis
	
	@Override
	public void run() {
		hashtagsForPoll.add("PIPA");
		hashtagsForPoll.add("SOPA");
		while (true) {
			try {
				String line = TwitterData.remove();
				if (line == null) {
					sleep(sleepWhenNoData);
				} else {
					twitter4j.internal.org.json.JSONObject jObject = new twitter4j.internal.org.json.JSONObject(line);
					Status status = new StatusJSONImpl(jObject);
					for (HashtagEntity hashtagEntity : status.getHashtagEntities()) {
						String hashtagString = hashtagEntity.getText();
						if (hashtagString.equals(hashtagForTweet)) {
							Tweets.add(status, hashtagString);
						} else {
							if (hashtagsForPoll.contains(hashtagString)) {
								Poll.newData(hashtagString);
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
