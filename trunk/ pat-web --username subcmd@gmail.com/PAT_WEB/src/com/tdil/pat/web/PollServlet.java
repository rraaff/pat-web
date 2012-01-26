package com.tdil.pat.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tdil.pat.model.Poll;

public class PollServlet extends HttpServlet {

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
		resp.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		resp.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		resp.setDateHeader ("Expires", -1);
		resp.setContentType("text/xml");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		out.append("<answer>");
		out.append("<question>").append(TweetsServlet.CDATA_START_TAG);
		out.append(String.valueOf(Poll.uniqueInstance().getName()));
		out.append(TweetsServlet.CDATA_END_TAG).append("</question>");
		out.append("<refreshInterval>");
		out.append(String.valueOf(Poll.uniqueInstance().getRefreshInterval()));
		out.append("</refreshInterval>");
		out.append("<options>");
		Map<String, AtomicInteger> currentData = com.tdil.pat.processing.Poll.getPollData();
		for (String option : Poll.uniqueInstance().getOptions()) {
			out.append("<option>");
			out.append("<name>").append(TweetsServlet.CDATA_START_TAG);
			out.append(option);
			out.append(TweetsServlet.CDATA_END_TAG).append("</name>");
			AtomicInteger atomicInteger = currentData.get(option);
			int votes = 0;
			if (atomicInteger != null) {
				votes = atomicInteger.intValue();
			}
			if (votes == 0) {
				votes = 1;
			}
			out.append("<votes>");
			out.append(String.valueOf(votes));
			out.append("</votes>");
			out.append("</option>");
		}
		out.append("</options>");
		out.append("</answer>");
		out.flush();
	}
}
