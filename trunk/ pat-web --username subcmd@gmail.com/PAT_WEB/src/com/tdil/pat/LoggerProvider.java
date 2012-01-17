/*
 * Created on Apr 22, 2004
 *
 */
package com.tdil.pat;

import java.util.Enumeration;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 */
public class LoggerProvider {
	
	private static boolean initialized = false;
	
	private static HashMap<String, Logger> loggers;
	
	/**
	 * Constructor 
	 */
	public LoggerProvider() {
		super();
	}
	
	/**
	 * Returns all available loggers in a hashmap
	 * 
	 * @return
	 */
	private static HashMap<String, Logger> getLoggers() {
		if (loggers == null) {
			setLoggers(new HashMap<String, Logger>());
		}
		return loggers;
	}
	
	/**
	 * Sets the loggers
	 * 
	 * @param newLoggers
	 */
	private static void setLoggers(HashMap<String, Logger> newLoggers) {
		loggers = newLoggers;
	}
	
	public static void destroy() {
		initialized = false;
		loggers = null;
	}
	
	/**
	 * Initialize the base logger with the given enumeration
	 * 
	 * @param loggersEnum
	 */
	public static synchronized void initialize(final String logFilePath, final Enumeration<Logger> loggersEnum) {
		if (!initialized) {
//			DOMConfigurator.configureAndWatch(logFilePath);
			DOMConfigurator.configure(logFilePath);
			Logger logger;
			while (loggersEnum.hasMoreElements()) {
				logger = loggersEnum.nextElement();
				if (logger != null && logger.getName() != null) {
					addLogger(logger.getName(), logger);
				}
			}
			initialized = true;
		}
	}
	
	/**
	 * Add a Logger into loggers
	 * 
	 * @param loggerName
	 * @param logger
	 */
	private static void addLogger(String loggerName, Logger logger) {
		getLoggers().put(loggerName, logger);
	}
	
	/**
	 * Returns the logger for the given logger name
	 * 
	 * @param className
	 * @return Logger
	 */
	public static Logger getLogger(Class<? extends Object> className) {
		return getLogger(className.getName());
	}
	
	/**
	 * Returns the logger for the given logger name
	 * 
	 * @param loggerName
	 * @return Logger
	 */
	private static Logger getLogger(String loggerName) {
		Logger logger = getLoggers().get(loggerName);
		//In the case that the loggerName represents a new logger
		//we create it and put it in the map
		if (logger == null) {
			logger = Logger.getLogger(loggerName);
			addLogger(loggerName, logger);
		}
		return logger;
	}
	

}
