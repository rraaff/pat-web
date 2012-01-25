package com.tdil.pat.processing;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.Status;
import twitter4j.User;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.PATSystem;
import com.tdil.pat.model.Hashtag;
import com.tdil.pat.processing.testing.ListRandom;
import com.tdil.pat.web.TweetsServlet;

public class Tweets {

	private static int index = 0;
	private static Status tweets[] = new Status[100];
	
	private static final int BACKUP_SIZE = 500;
	private static int backupSize = 0;
	private static int backupIndex = 0;
	private static Status backup[] = new Status[BACKUP_SIZE];

	private static boolean needsBackup = false;
	private static Object backupmutex = new Object();
	private static Object mutex = new Object();
	
	public static Logger LOG = LoggerProvider.getLogger(Tweets.class);

	public static void add(Status status, String hashtag) {
		//System.out.println("t+" + hashtag);
		synchronized (mutex) {
//			System.out.println("Adding " + index);
			tweets[index] = status;
			index = (index + 1) % 100;
		}
		synchronized (backupmutex) {
			backup[backupIndex] = status;
			if (backupIndex + 1 == BACKUP_SIZE) {
				needsBackup = true;
			}
		}
		backupSize = backupSize + 1;
		if (backupSize > BACKUP_SIZE) {
			backupSize = BACKUP_SIZE;
		}
		backupIndex = (backupIndex + 1) % BACKUP_SIZE;
	}

	public static void writeBackupToDisk()  {
		if (!needsBackup) {
			BackupThread.LOG.warn("Nothing to backup");
			return;
		}
		BackupThread.LOG.warn("Making backup");
		Status[] backup2 = new Status[BACKUP_SIZE];
		synchronized (backupmutex) {
			System.arraycopy(backup, 0, backup2, 0, BACKUP_SIZE);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileOutputStream fout = null;
		BufferedOutputStream buffOut = null;
		try {
			fout = new FileOutputStream(PATSystem.databasePath + "/backup.xml");
			buffOut = new BufferedOutputStream(fout);
			buffOut.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>".getBytes());
			buffOut.write("<answer>".getBytes());
			buffOut.write("<hashtag>".getBytes());
			buffOut.write(TweetsServlet.CDATA_START_TAG.getBytes());
			buffOut.write(String.valueOf(Hashtag.uniqueInstance().getHashtag()).getBytes());
			buffOut.write(TweetsServlet.CDATA_END_TAG.getBytes());
			buffOut.write("</hashtag>".getBytes());
			buffOut.write("<refreshInterval>".getBytes());
			buffOut.write(String.valueOf(Hashtag.uniqueInstance().getRefreshInterval()).getBytes());
			buffOut.write("</refreshInterval>".getBytes());
			buffOut.write("<readingInterval>".getBytes());
			buffOut.write(String.valueOf(Hashtag.uniqueInstance().getReadingInterval()).getBytes());
			buffOut.write("</readingInterval>".getBytes());
			buffOut.write("<tweets>".getBytes());
			for (Status status : backup2) {
				buffOut.write("<tweet>".getBytes());
				User user = status.getUser();
				buffOut.write("<screenName>".getBytes());
				buffOut.write(TweetsServlet.CDATA_START_TAG.getBytes());
				buffOut.write(user.getScreenName().getBytes());
				buffOut.write(TweetsServlet.CDATA_END_TAG.getBytes());
				buffOut.write("</screenName>".getBytes());
				buffOut.write("<userName>".getBytes());
				buffOut.write(TweetsServlet.CDATA_START_TAG.getBytes());
				buffOut.write(user.getName().getBytes());
				buffOut.write(TweetsServlet.CDATA_END_TAG.getBytes());
				buffOut.write("</userName>".getBytes());
				buffOut.write("<profileImageURL>".getBytes());
				buffOut.write(TweetsServlet.CDATA_START_TAG.getBytes());
				buffOut.write((user.getProfileImageURL() != null ? user.getProfileImageURL().toString() : "").getBytes());
				buffOut.write(TweetsServlet.CDATA_END_TAG.getBytes());
				buffOut.write("</profileImageURL>".getBytes());
				buffOut.write("<text>".getBytes());
				buffOut.write(TweetsServlet.CDATA_START_TAG.getBytes());
				buffOut.write(status.getText().getBytes());
				buffOut.write(TweetsServlet.CDATA_END_TAG.getBytes());
				buffOut.write("</text>".getBytes());
				buffOut.write("<createdAt>".getBytes());
				buffOut.write(dateFormat.format(status.getCreatedAt()).getBytes());
				buffOut.write("</createdAt>".getBytes());
				buffOut.write("</tweet>".getBytes());
			}
			buffOut.write("</tweets>".getBytes());
			buffOut.write("</answer>".getBytes());
			buffOut.flush();
			needsBackup = false;
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
			}
			try {
				buffOut.close();
			} catch (IOException e) {
			}
		}
		
	}

	public static List<Status> last(int maxResultSize) {
		int currIndex = 0;
		List<Status> result = new ArrayList<Status>();
		synchronized (mutex) {
			// copio el original
			currIndex = index - 1;
			currIndex = currIndex < 0 ? 99 : currIndex;
			int resultSize = 0;
			while (resultSize < maxResultSize && tweets[currIndex] != null) {
				result.add(tweets[currIndex]);
				tweets[currIndex] = null;
				resultSize = resultSize + 1;
				currIndex = currIndex - 1 < 0 ? 99 : currIndex - 1;
			}
		}
		if (result.size() < maxResultSize) {
			LOG.warn("not enough status, randomizing repeated");
			if (backupSize > 0) {
				List<Status> random = new ArrayList<Status>();
				for (int i = 0; i < backupSize; i++) {
					random.add(backup[i]);
				}
				new ListRandom(random.size()).randomize(random);
				for (int i = result.size(); i < maxResultSize; i++) {
					result.add(random.get(i % random.size()));
				}
			}
		}
		return result;
	}

}
