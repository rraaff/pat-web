package com.tdil.pat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;

import com.tdil.pat.model.FakeTweet;
import com.tdil.pat.model.FilteredWord;
import com.tdil.pat.model.Hashtag;
import com.tdil.pat.model.Poll;
import com.tdil.pat.model.TwitterAccount;
import com.tdil.pat.model.User;
import com.tdil.pat.processing.BackupThread;
import com.tdil.pat.processing.DataSeparator;
import com.tdil.pat.processing.TwitterCollector;

public class PATSystem implements ServletContextListener {

	public static String databasePath;
	
	public void contextInitialized(ServletContextEvent sce) {
		try {
			databasePath = System.getProperty("pat.databaseLocation");
			startup();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// nothing to do
	}
	
	public static void startup() throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		File log4j = new File(databasePath + "log4j.xml");
		if(log4j.exists() && log4j.length() < 1400) {
			log4j.delete();
			InputStream io = PATSystem.class.getResourceAsStream("log4j.xml");
			IOUtils.copy(io, new FileOutputStream(databasePath + "log4j.xml"));
		} else {
			if (!log4j.exists()) {
				InputStream io = PATSystem.class.getResourceAsStream("log4j.xml");
				IOUtils.copy(io, new FileOutputStream(databasePath + "log4j.xml"));
			}
		}
		initLogger();
		User.readAll();
		FilteredWord.readAll();
		FakeTweet.readAll();
		TwitterAccount.readAll();
		Hashtag.readAll();
		Poll.readAll();
		new TwitterCollector().start();
		new DataSeparator().start();
		new BackupThread().start();
	}
	
	private static void initLogger() {
		String logFilePath = databasePath + "log4j.xml";
		LoggerProvider.initialize(logFilePath, LogManager.getCurrentLoggers());
	}
}
