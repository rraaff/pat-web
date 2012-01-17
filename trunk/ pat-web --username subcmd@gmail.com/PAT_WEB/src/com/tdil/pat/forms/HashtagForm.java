package com.tdil.pat.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.tdil.pat.model.Hashtag;

public class HashtagForm extends ActionForm {

	private static final long serialVersionUID = 7670249948557986182L;

	private String operation;
	private Hashtag edited;
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		edited = null;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return edited;
	}
	public void setEdited(Hashtag edited) {
		this.edited = edited;
	}
	
}
