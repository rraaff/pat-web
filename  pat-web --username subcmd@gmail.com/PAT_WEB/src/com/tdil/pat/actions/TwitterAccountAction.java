package com.tdil.pat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.forms.TwitterAccountForm;
import com.tdil.pat.model.TwitterAccount;

public class TwitterAccountAction extends PATAction  {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!validateLogin(request)) {
			return mapping.findForward("login");
		}
		TwitterAccountForm aForm = (TwitterAccountForm) form;
		if (aForm.getOperation().equals("Guardar")) {
			TwitterAccount.modify(aForm.getEdited());
		}
		return mapping.findForward("continue");			

	}


}
