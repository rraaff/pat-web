package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.Status;

import com.tdil.cvs.CVSUtils;
import com.tdil.pat.LoggerProvider;
import com.tdil.pat.text.FilterMode;
import com.tdil.pat.text.FilterProfanity;

public class Hashtag {

	private String hashtag;
	private boolean active;
	private boolean filtering;
	private String filteringMode;
	
	private long refreshInterval;
	private int maxTweetsToAnswer;
	
	// todo agregar refresh time y max cantidad a retornar

	private static String headers[] = {"hashtag","active","filtering","filteringMode","refreshInterval","maxTweetsToAnswer"};
	private static List<Hashtag> instances = new ArrayList<Hashtag>();
	
	private static Logger LOG = LoggerProvider.getLogger(Hashtag.class);
	
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
	
	public long getRefreshInterval() {
		return refreshInterval;
	}
	public void setRefreshInterval(long refreshInterval) {
		this.refreshInterval = refreshInterval;
	}
	public int getMaxTweetsToAnswer() {
		return maxTweetsToAnswer;
	}
	public void setMaxTweetsToAnswer(int maxTweetsToAnswer) {
		this.maxTweetsToAnswer = maxTweetsToAnswer;
	}
	
	public static void modify(List<Hashtag> hashtags) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		CVSUtils.write("hashtag.csv", headers, hashtags);
		instances = hashtags;
	}
	
	public static void modify(Hashtag hashtag) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		hashtags.add(hashtag);
		modify(hashtags);
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		readAll();
		Hashtag newhashtag = new Hashtag();
		newhashtag.setHashtag("mdq");
		newhashtag.setActive(true);
		newhashtag.setFiltering(true);
		newhashtag.setFilteringMode("replace");
		List<Hashtag> modif = new ArrayList<Hashtag>();
		modif.add(newhashtag);
		modify(modif);
		
	}
	public static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		LOG.fatal("reading hashtag");
		CVSUtils.read("hashtag.csv", headers, Hashtag.class, instances);
		LOG.fatal("hashtag read");
	}
	
	public static Hashtag uniqueInstance() {
		return instances.get(0);
	}
	public boolean approvesFilter(Status status) {
		if (this.isFiltering()) {
			FilterProfanity filter = FilteredWord.getFilterProfanity();
			filter.setFilterMode(filteringMode.equals(FilterMode.REPLACE_WORD.name()) ? FilterMode.REPLACE_WORD : FilterMode.REJECT_TEXT);
			String result = filter.filterProfanity(status.getText());
			if (result != null) {
				status.setText(result);
			}
			return result != null;
		} else {
			return true;
		}
	}

}
