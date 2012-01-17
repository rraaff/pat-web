/*
 * Created on Mar 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tdil.pat.log4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Class <code>LoggerMonitorAppender</code> is an appender that logs the
 * errors to the logger monitor.
 * 
 * @author Marcos Godoy
 */
public class LoggerMonitorAppender extends AppenderSkeleton {

	private static List<LogError> errors = new ArrayList<LogError>();
	
	// TODO Esto debe ser una sysproperty
	private static int maxExceptions = 20;
	
	/**
	 * Default Constructor
	 */
	public LoggerMonitorAppender() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.Appender#close()
	 */
	public void close() {
		// DO NOTHING
	}
	
	/* (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	protected void append(LoggingEvent arg0) {
		if (maxExceptions != 0 && arg0.getLevel() == Level.ERROR) {
			if (errors.size() >= maxExceptions) {
				errors.remove(0);
			}
			Throwable throwable = arg0.getThrowableInformation() == null ? null : arg0.getThrowableInformation().getThrowable();
			errors.add(new LogError(throwable));
		} 
	}

	public static List<LogError> getErrors() {
		return errors;
	}
}
