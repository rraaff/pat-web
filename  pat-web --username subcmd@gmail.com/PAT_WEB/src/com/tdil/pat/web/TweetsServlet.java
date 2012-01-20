package com.tdil.pat.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Status;
import twitter4j.User;

import com.tdil.pat.model.Hashtag;
import com.tdil.pat.processing.Tweets;

public class TweetsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4570360669857999122L;
	public static final String CDATA_START_TAG = "<![CDATA[";
	public static final String CDATA_END_TAG = "]]>";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

	private void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Status> answer = Tweets.last(Hashtag.uniqueInstance().getMaxTweetsToAnswer());
		resp.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		resp.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		resp.setDateHeader ("Expires", -1);
		resp.setContentType("text/xml");
		PrintWriter out = resp.getWriter();
		out.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		out.append("<answer>");
		out.append("<hashtag>").append(CDATA_START_TAG);
		out.append(String.valueOf(Hashtag.uniqueInstance().getHashtag()));
		out.append(CDATA_END_TAG).append("</hashtag>");
		out.append("<refreshInterval>");
		out.append(String.valueOf(Hashtag.uniqueInstance().getRefreshInterval()));
		out.append("</refreshInterval>");
		out.append("<tweets>");
		for (Status status : answer) {
			out.append("<tweet>");
			User user = status.getUser();
			out.append("<screenName>").append(CDATA_START_TAG);
			out.append(user.getScreenName());
			out.append(CDATA_END_TAG).append("</screenName>");
			out.append("<userName>").append(CDATA_START_TAG);
			out.append(user.getName());
			out.append(CDATA_END_TAG).append("</userName>");
			out.append("<profileImageURL>").append(CDATA_START_TAG);
			out.append(user.getProfileImageURL() != null ? user.getProfileImageURL().toString() : "");
			out.append(CDATA_END_TAG).append("</profileImageURL>");
			out.append("<text>").append(CDATA_START_TAG);
			out.append(status.getText());
			out.append(CDATA_END_TAG).append("</text>");
			out.append("<createdAt>");
			out.append(dateFormat.format(status.getCreatedAt()));
			out.append("</createdAt>");
			out.append("</tweet>");
		}
		out.append("</tweets>");
		out.append("</answer>");
		out.flush();
	}
}
