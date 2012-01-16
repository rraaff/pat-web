package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tdil.cvs.CVSUtils;

public class Hashtag {

	private String hashtag;
	private boolean active;
	private boolean filtering;
	private String filteringMode;
	private long feedCheckInterval;

	private static String headers[] = {"hashtag","active","filtering","filteringMode","feedCheckInterval"};
	private static List<Hashtag> instances = new ArrayList<Hashtag>();
	
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isFiltering() {
		return filtering;
	}
	public void setFiltering(boolean filtering) {
		this.filtering = filtering;
	}
	public String getFilteringMode() {
		return filteringMode;
	}
	public void setFilteringMode(String filteringmode) {
		this.filteringMode = filteringmode;
	}
	public long getFeedCheckInterval() {
		return feedCheckInterval;
	}
	public void setFeedCheckInterval(long feedcheckinterval) {
		this.feedCheckInterval = feedcheckinterval;
	}
	
	public static void modify(List<Hashtag> hashtags) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		CVSUtils.write("hashtag.csv", headers, hashtags);
		instances = hashtags;
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		readAll();
		Hashtag newhashtag = new Hashtag();
		newhashtag.setHashtag("mdq");
		newhashtag.setActive(true);
		newhashtag.setFiltering(true);
		newhashtag.setFilteringMode("remove");
		newhashtag.setFeedCheckInterval(350);
		List<Hashtag> modif = new ArrayList<Hashtag>();
		modif.add(newhashtag);
		modify(modif);
		
	}
	private static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		CVSUtils.read("hashtag.csv", headers, Hashtag.class, instances);
	}
}
