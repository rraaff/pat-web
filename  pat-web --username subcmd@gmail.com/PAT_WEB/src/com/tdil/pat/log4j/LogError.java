/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.tdil.pat.log4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Class <code>LogError</code> is the class that represents
 * an error logged by the log4j engine.
 * 
 * @author Marcos Godoy
 */
public class LogError {
	
	private Date date = new Date();

	private String exceptionMessage = "";
	private String exceptionStackTrace = "";
	
	private static final int MAX_EXCEPTION_LENGTH = 5000;
	
	public LogError(Throwable exception) {
		super();
		this.setException(exception);
	}
	
	/**
	 * @param throwable
	 */
	private void setException(Throwable throwable) {
		if (throwable != null) {
			if (throwable.getMessage() != null) {
				setExceptionMessage(throwable.getMessage());
			}
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(byteOut);
			throwable.printStackTrace(printStream);
			String st = byteOut.toString();
			if (st.length() > MAX_EXCEPTION_LENGTH) {
				this.setExceptionStackTrace(st.substring(0, MAX_EXCEPTION_LENGTH));
			} else {
				this.setExceptionStackTrace(st);
			}
		}
	}

	private void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	/**
	 * Accesor
	 */
	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	private void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public Date getDate() {
		return date;
	}
}