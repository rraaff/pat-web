package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tdil.cvs.CVSUtils;

public class Poll {

	private String name;
	private boolean active;
	private long feedCheckInterval;
	private List<String> options = new ArrayList<String>();

	private static String headers[] = { "name", "active", "feedCheckInterval","optionsList" };
	private static List<Poll> instances = new ArrayList<Poll>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFeedCheckInterval() {
		return feedCheckInterval;
	}

	public void setFeedCheckInterval(long feedCheckInterval) {
		this.feedCheckInterval = feedCheckInterval;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getOptionsList() {
		StringBuffer result = new StringBuffer();
		int index = 0;
		int max = getOptions().size();
		for (String st : getOptions()) {
			result.append(st);
			if (index < max - 1) {
				result.append(",");
			}
			index = index + 1;
		}
		return result.toString();
	}
	
	public void setOptionsList(String optionsList) {
		String list[] = optionsList.split(",");
		for (String opt : list) {
			getOptions().add(opt);
		}
	}

	public List<String> getOptions() {
		return options;
	}
	
	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	public static void modify(List<Poll> polls) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		CVSUtils.write("poll.csv", headers, polls);
		instances = polls;
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		readAll();
//		Poll poll = new Poll();
//		poll.setName("Best");
//		poll.setActive(true);
//		poll.setFeedCheckInterval(132);
//		poll.getOptions().add("aldanah");
//		poll.getOptions().add("holly");
//		poll.getOptions().add("otras");
//		instances.add(poll);
//		modify(instances);
	}

	public static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		CVSUtils.read("poll.csv", headers, Poll.class, instances);
	}

}
