package com.tdil.pat.processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class TwitterStream extends AbstractTwitterStream {

	private HttpURLConnection connection = null;
	private BufferedReader in = null;
	
	public TwitterStream(String username, String password, String track) {
		super(username, password, track);
	}

	@Override
	public void connect() throws Exception {
		URL url = new URL("https://stream.twitter.com/1/statuses/filter.json?track=" + this.getTrack());
		String encoding = new BASE64Encoder().encode((this.getUsername() + ":" + this.getPassword()).getBytes());
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization", "Basic " + encoding);
		InputStream content = (InputStream) connection.getInputStream();
		in = new BufferedReader(new InputStreamReader(content));
	}

	@Override
	public void disconnect() {
		if (connection != null) {
			connection.disconnect();
		}

	}

	@Override
	public String nextLine() throws IOException {
		return in.readLine();
	}

}
