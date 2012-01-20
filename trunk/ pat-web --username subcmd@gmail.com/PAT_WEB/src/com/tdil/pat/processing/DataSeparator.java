package com.tdil.pat.processing;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.internal.json.StatusJSONImpl;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.model.Hashtag;

public class DataSeparator extends Thread {

	private static long sleepWhenNoData = 10; // 10 millis
	
	private static Logger LOG = LoggerProvider.getLogger(DataSeparator.class);
	
	@Override
	public void run() {
		while (true) {
			try {
				// esto tendria que esar afuera de otro loop interno?
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
							if (Hashtag.uniqueInstance().approvesFilter(status)) {
								Tweets.add(status, hashtagString);
							} else {
								if (LOG.isDebugEnabled()) {
									LOG.debug("DataSeparator tweet rejected " + status.getText());
								}
							}
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
