package com.tdil.pat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.forms.PollForm;
import com.tdil.pat.model.Poll;

public class PollAction extends PATAction  {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!validateLogin(request)) {
			return mapping.findForward("login");
		}
		PollForm PollForm = (PollForm) form;
		if (PollForm.getOperation().equals("Guardar")) {
			Poll.modify(PollForm.getEdited());
		}
		if (PollForm.getOperation().equals("Desactivar")) {
			PollForm.getEdited().setActive(false);
			Poll.modify(PollForm.getEdited());
			// reseto la encuesta???
		}
		if (PollForm.getOperation().equals("Activar")) {
			PollForm.getEdited().setActive(true);
			Poll.modify(PollForm.getEdited());
			// reseto la encuesta???
		}
		return mapping.findForward("continue");			

	}


}
