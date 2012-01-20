package com.tdil.pat.processing;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.model.Hashtag;
import com.tdil.pat.model.Poll;
import com.tdil.pat.model.TwitterAccount;

public class TwitterCollector extends Thread {

	private String username;
	private String password;
	private String track = "";
	private Set<String> options = new HashSet<String>();

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
				this.setTrack(Hashtag.uniqueInstance().getHashtag());
				// TODO las opciones
				waitBeforeConnecting(connectAttemps);
				LOG.warn("TwitterCollector starting to collect " + this.getTrackData() + " -u" + username + ":" + password);
				AbstractTwitterStream abstractTwitterStream = null;
				// si empieza con # va de file
				if (username.startsWith("#")) {
					LOG.warn("TwitterCollector using fake file stream");
					abstractTwitterStream = new FileTwitterStream(this.getUsername(), this.getPassword(), this.getTrackData());
				} else {
					LOG.warn("TwitterCollector using real Twitter");
					abstractTwitterStream = new TwitterStream(this.getUsername(), this.getPassword(), this.getTrackData());
				}				
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
	
	public String getTrackData() {
		return this.getTrack(); // TODO las opciones
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
		if (!this.getTrack().equals(Hashtag.uniqueInstance().getHashtag())) {
			return true;
		}
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
