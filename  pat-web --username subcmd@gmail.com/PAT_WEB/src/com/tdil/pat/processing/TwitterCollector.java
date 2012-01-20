package com.tdil.pat.processing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.model.Hashtag;
import com.tdil.pat.model.Poll;
import com.tdil.pat.model.TwitterAccount;

public class TwitterCollector extends Thread {

	private String username;
	private String password;
	private String track = "TheresNoReason";

	private int connectAttemps = 0;

	private static final long sleepPerAttempt = 10000; // 10 seconds
	
	private static Logger LOG = LoggerProvider.getLogger(TwitterCollector.class);
	
	@Override
	public void run() {
		while (true) {
			// Toma los datos nuevamente
			username = TwitterAccount.uniqueInstance().getUsername();
			password = TwitterAccount.uniqueInstance().getPassword();
			if (this.isCollecting()) {
				waitBeforeConnecting(connectAttemps);
				LOG.warn("TwitterCollector starting to collect " + this.getTrack() + "-u" + username + ":" + password);
				//AbstractTwitterStream abstractTwitterStream = new TwitterStream(this.getUsername(), this.getPassword(), this.getTrack());
				AbstractTwitterStream abstractTwitterStream = new FileTwitterStream(this.getUsername(), this.getPassword(), this.getTrack());
				try {
					abstractTwitterStream.connect();
					String line;
					while ((line = abstractTwitterStream.nextLine()) != null && !this.requiresRestart()) {
						TwitterData.add(line);
						//System.out.println(line);
						connectAttemps = 0;
					}
				} catch (Exception e) {
					connectAttemps = connectAttemps + 1;
					LOG.error(e.getMessage(), e);
				} finally {
					LOG.warn("TwitterCollector disconnecting");
					abstractTwitterStream.disconnect();
				}
			} else {
				try {
					LOG.warn("TwitterCollector not collecting, sleeping");
					sleep(1000);
				} catch (InterruptedException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	private void waitBeforeConnecting(int connectAttemps) {
		try {
			LOG.warn("TwitterCollector waiting to connect " + (sleepPerAttempt * connectAttemps));
			sleep(sleepPerAttempt * connectAttemps);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private boolean requiresRestart() {
		if (twitterAccountHasChanged()) {
			return true;
		}
		if (trackDataHasChanged()) {
			return true;
		}
		return false;
	}

	private boolean trackDataHasChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean twitterAccountHasChanged() {
		if (!this.getUsername().equals(TwitterAccount.uniqueInstance().getUsername())) {
			return true;
		}
		if (!this.getPassword().equals(TwitterAccount.uniqueInstance().getPassword())) {
			return true;
		}
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

	public int getConnectAttemps() {
		return connectAttemps;
	}

	public void setConnectAttemps(int connectAttemps) {
		this.connectAttemps = connectAttemps;
	}

	public boolean isCollecting() {
		return Hashtag.uniqueInstance().isActive() || Poll.uniqueInstance().isActive();
	}

}
