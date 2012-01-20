package com.tdil.pat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

public class PATAction extends Action {

	protected boolean validateLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		com.tdil.pat.model.User user = (com.tdil.pat.model.User)session.getAttribute("user");
		return user != null;
	}
}
