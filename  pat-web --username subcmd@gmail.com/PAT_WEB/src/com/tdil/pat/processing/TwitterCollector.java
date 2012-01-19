package com.tdil.pat.processing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class TwitterCollector extends Thread {

	private String username = "subcmd_";
	private String password = "nojodas";
	private String track = "TheresNoReason";

	private int connectAttemps = 0;
	private boolean collecting = false;
	private boolean restart = false;

	private static final long sleepPerAttempt = 10000; // 10 seconds
	
	@Override
	public void run() {
		while (true) {
			if (this.isCollecting()) {
				waitBeforeConnecting(connectAttemps);
				HttpURLConnection connection = null;
				try {
					URL url = new URL("https://stream.twitter.com/1/statuses/filter.json?track=" + this.getTrack());
					String encoding = new BASE64Encoder().encode((this.getUsername() + ":" + this.getPassword()).getBytes());
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					connection.setRequestProperty("Authorization", "Basic " + encoding);
					InputStream content = (InputStream) connection.getInputStream();
					BufferedReader in = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = in.readLine()) != null && !this.requiresRestart()) {
						TwitterData.add(line);
						//System.out.println(line);
						connectAttemps = 0;
					}
				} catch (Exception e) {
					connectAttemps = connectAttemps + 1;
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}
	}

	private void waitBeforeConnecting(int connectAttemps) {
		try {
			sleep(sleepPerAttempt * connectAttemps);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean requiresRestart() {
		// TODO Auto-generated method stub
		return false;
	}

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

	public boolean isRestart() {
		return restart;
	}

	public void setRestart(boolean restart) {
		this.restart = restart;
	}

	public int getConnectAttemps() {
		return connectAttemps;
	}

	public void setConnectAttemps(int connectAttemps) {
		this.connectAttemps = connectAttemps;
	}

	public boolean isCollecting() {
		return collecting;
	}

	public void setCollecting(boolean collecting) {
		this.collecting = collecting;
	}
}
