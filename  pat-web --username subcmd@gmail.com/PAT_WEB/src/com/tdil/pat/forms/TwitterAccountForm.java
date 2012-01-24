package com.tdil.pat.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.model.TwitterAccount;

public class TwitterAccountForm extends ActionForm {

	private static final long serialVersionUID = 7670249948557986182L;

	private String operation;
	private TwitterAccount edited;
	
	private static Logger LOG = LoggerProvider.getLogger(TwitterAccountForm.class);
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public TwitterAccount getEdited() {
		if (edited == null) {
			edited = new TwitterAccount();
			try {
				BeanUtils.copyProperties(edited, TwitterAccount.uniqueInstance());
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return edited;
	}
	public void setEdited(TwitterAccount edited) {
		this.edited = edited;
	}
	
}
