package com.tdil.pat.processing;

import java.util.HashSet;
import java.util.Set;

import com.tdil.pat.model.Hashtag;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.internal.json.StatusJSONImpl;

public class DataSeparator extends Thread {

	private static long sleepWhenNoData = 10; // 10 millis
	
	@Override
	public void run() {
		while (true) {
			try {
				String hashtagForTweet = Hashtag.uniqueInstance().getHashtag();
				Set<String> hashtagsForPoll = new HashSet<String>(); 
				hashtagsForPoll.addAll(com.tdil.pat.model.Poll.uniqueInstance().getOptions());
				String line = TwitterData.remove();
				if (line == null) {
					sleep(sleepWhenNoData);
				} else {
					twitter4j.internal.org.json.JSONObject jObject = new twitter4j.internal.org.json.JSONObject(line);
					Status status = new StatusJSONImpl(jObject);
					for (HashtagEntity hashtagEntity : status.getHashtagEntities()) {
						String hashtagString = hashtagEntity.getText();
						if (hashtagString.equals(hashtagForTweet)) {
							// TODO pasaje por el filtro loco si esta activo
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
