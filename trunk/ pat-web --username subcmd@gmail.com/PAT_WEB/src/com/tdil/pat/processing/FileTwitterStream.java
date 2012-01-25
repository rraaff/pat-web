package com.tdil.pat.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.tdil.pat.processing.testing.FileCollector;
import com.tdil.pat.processing.testing.ListRandom;

public class FileTwitterStream extends AbstractTwitterStream {

	private List<String> lines;
	private int index = 0;
	
	public FileTwitterStream(String username, String password, String track) {
		super(username, password, track);
	}

	@Override
	public void connect() throws Exception {
		lines = new ArrayList<String>();
		lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets.txt"))); // muchos sin hashtag
		lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets1.txt"))); // solo hashtag
		lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets2.txt"))); // hashtags and polls
		lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets3.txt"))); // puteadas
		new ListRandom(lines.size()).randomize(lines); // random
	}

	@Override
	public void disconnect() {

	}

	@Override
	public String nextLine() throws IOException {
		if (index >= lines.size()) {
			index = 0;
		}
		//sleep?
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines.get(index++);
	}

}
