package com.tdil.pat.web;

import java.io.IOException;
import java.io.PrintWriter;
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

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

	private void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<Status> answer = Tweets.last(Hashtag.uniqueInstance().getMaxTweetsToAnswer());
		PrintWriter out = resp.getWriter();
		out.append("<answer>");
		out.append("<refreshInterval>");
		out.append(String.valueOf(Hashtag.uniqueInstance().getRefreshInterval()));
		out.append("</refreshInterval>");
		out.append("<tweets>");
		for (Status status : answer) {
			out.append("<tweet>");
			User user = status.getUser();
			out.append("<screenName>");
			out.append(user.getScreenName());
			out.append("</screenName>");
			out.append("<userName>");
			out.append(user.getName());
			out.append("</userName>");
			out.append("<profileImageURL>");
			out.append(user.getProfileImageURL() != null ? user.getProfileImageURL().toString() : "");
			out.append("</profileImageURL>");
			// TODO
			out.append("</tweet>");
		}
		out.append("</tweets>");
		out.append("</answer>");
		out.flush();
	}
}
