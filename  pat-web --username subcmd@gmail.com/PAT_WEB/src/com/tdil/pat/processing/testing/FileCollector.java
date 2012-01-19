package com.tdil.pat.processing.testing;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.tdil.pat.processing.TwitterData;

public class FileCollector extends Thread {

	private static final long sleep = 10; // 10 millis
	
	@Override
	public void run() {
		try {
			List<String> lines = IOUtils.readLines(FileCollector.class.getResourceAsStream("tweets.txt"));
			while (true) {
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
