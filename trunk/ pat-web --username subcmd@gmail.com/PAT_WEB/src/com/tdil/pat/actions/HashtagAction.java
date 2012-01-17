package com.tdil.pat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.forms.HashtagForm;

public class HashtagAction extends Action  {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashtagForm hashtagForm = (HashtagForm) form;
		return mapping.findForward("continue");			

	}


}
