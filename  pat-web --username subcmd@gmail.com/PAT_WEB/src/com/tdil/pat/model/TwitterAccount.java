package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tdil.cvs.CVSUtils;
import com.tdil.pat.LoggerProvider;

public class TwitterAccount {

	private String username;
	private String password;
	
	private static String headers[] = {"username","password"};
	private static List<TwitterAccount> instances = new ArrayList<TwitterAccount>();
	
	private static Logger LOG = LoggerProvider.getLogger(TwitterAccount.class);
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static void modify(List<TwitterAccount> twitter) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		CVSUtils.write("twitter.csv", headers, twitter);
		instances = twitter;
	}
	
	public static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		LOG.fatal("reading twitter account");
		CVSUtils.read("twitter.csv", headers, TwitterAccount.class, instances);
		LOG.fatal("twitter account read");
	}
	
	public static TwitterAccount uniqueInstance() {
		return instances.get(0);
	}
}
