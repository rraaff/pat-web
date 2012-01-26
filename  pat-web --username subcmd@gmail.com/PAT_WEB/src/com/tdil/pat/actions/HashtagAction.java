package com.tdil.pat.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.forms.HashtagForm;
import com.tdil.pat.model.Hashtag;
import com.tdil.pat.processing.Tweets;

public class HashtagAction extends PATAction  {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!validateLogin(request)) {
			return mapping.findForward("login");
		}
		HashtagForm hashtagForm = (HashtagForm) form;
		if (hashtagForm.getOperation().equals("Guardar")) {
			if (!hashtagForm.getEdited().getHashtag().equals(Hashtag.uniqueInstance().getHashtag())) {
				Tweets.resetbackup();
			}
			Hashtag.modify(hashtagForm.getEdited());
		}
		if (hashtagForm.getOperation().equals("Desactivar")) {
			if (!hashtagForm.getEdited().getHashtag().equals(Hashtag.uniqueInstance().getHashtag())) {
				Tweets.resetbackup();
			}
			hashtagForm.getEdited().setActive(false);
			Hashtag.modify(hashtagForm.getEdited());
		}
		if (hashtagForm.getOperation().equals("Activar")) {
			if (!hashtagForm.getEdited().getHashtag().equals(Hashtag.uniqueInstance().getHashtag())) {
				Tweets.resetbackup();
			}
			hashtagForm.getEdited().setActive(true);
			Hashtag.modify(hashtagForm.getEdited());
		}
		return mapping.findForward("continue");			

	}

}
