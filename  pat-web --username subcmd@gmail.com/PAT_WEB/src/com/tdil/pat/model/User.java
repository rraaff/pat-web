package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tdil.cvs.CVSUtils;

public class User {

	private String username;
	private String password;
	
	private static String headers[] = {"username","password"};
	private static List<User> instances = new ArrayList<User>();

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
	
	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		readAll();
	}
	
	private static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		CVSUtils.read("user.csv", headers, User.class, instances);
	}
	
	
}
