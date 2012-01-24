package com.tdil.pat.processing;

import org.apache.log4j.Logger;

import com.tdil.pat.LoggerProvider;

public class BackupThread extends Thread {

	private static long sleepBetweenRuns = 10000; // 10 segundos
	
	public static Logger LOG = LoggerProvider.getLogger(BackupThread.class);
	
	@Override
	public void run() {
		while (true) {
			try {
				sleep(sleepBetweenRuns);
				Tweets.writeBackupToDisk();
			} catch (InterruptedException e) {
				throw new RuntimeException("BackupThread died");
			}
			
		}
	}
}
