package com.tdil.pat.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.LoggerProvider;
import com.tdil.pat.model.Hashtag;

public class HashtagForm extends ActionForm {

	private static final long serialVersionUID = 7670249948557986182L;

	private String operation;
	private Hashtag edited;
	
	private static Logger LOG = LoggerProvider.getLogger(HashtagForm.class);
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		getEdited().setFiltering(false);
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Hashtag getEdited() {
		if (edited == null) {
			edited = new Hashtag();
			try {
				BeanUtils.copyProperties(edited, Hashtag.uniqueInstance());
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return edited;
	}
	public void setEdited(Hashtag edited) {
		this.edited = edited;
	}
	
}
