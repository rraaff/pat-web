package com.tdil.pat.processing;

import java.io.IOException;


public abstract class AbstractTwitterStream {

	private String username;
	private String password;
	private String track;

	public AbstractTwitterStream(String username, String password, String track) {
		super();
		this.username = username;
		this.password = password;
		this.track = track;
	}

	public abstract void connect() throws Exception;
	
	public abstract void disconnect();
	
	public abstract String nextLine() throws IOException;

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

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}
}
