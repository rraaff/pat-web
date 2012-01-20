package com.tdil.pat.processing.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.processing.TwitterData;

public class FileCollector extends Thread {

	private static final long sleep = 2; // 10 millis
	
	private static Logger LOG = LoggerProvider.getLogger(FileCollector.class);
	
	@Override
	public void run() {
		try {
			List<String> lines = new ArrayList<String>();
			lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets.txt"))); // muchos sin hashtag
			lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets1.txt"))); // solo hashtag
			lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets2.txt"))); // hashtags and polls
			lines.addAll(IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets3.txt"))); // puteadas
			new ListRandom(lines.size()).randomize(lines); // random
			while (true) {
				LOG.warn("FileCollector simulating burst " + lines.size());
				for (String line : lines) {
					TwitterData.add(line);
					sleep(sleep);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
