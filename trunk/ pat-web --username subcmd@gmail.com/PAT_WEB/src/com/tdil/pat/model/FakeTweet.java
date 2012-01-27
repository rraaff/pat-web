package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tdil.cvs.CVSUtils;
import com.tdil.pat.LoggerProvider;

public class FakeTweet {

	// replacement is #hashtag#

	private String screenName;
	private String userName;
	private String text;
	private String profileImageURL;

	private static String headers[] = { "screenName", "userName", "profileImageURL", "text" };
	private static List<FakeTweet> instances = new ArrayList<FakeTweet>();
	
	private static Logger LOG = LoggerProvider.getLogger(FakeTweet.class);

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
	public static void readAll() throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
		LOG.fatal("reading fake tweets");
		CVSUtils.read("faketweets.csv", headers, FakeTweet.class, instances);
		LOG.fatal("fake tweets read");
	}

	public static List<FakeTweet> getInstances() {
		return instances;
	}
}
