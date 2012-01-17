package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tdil.cvs.CVSUtils;

public class FilteredWord {

	private String word;
	
	private static String headers[] = {"word"};
	private static List<FilteredWord> instances = new ArrayList<FilteredWord>();

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		readAll();
	}
	
	public static void readAll() throws IOException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		CVSUtils.read("filteredword.csv", headers, FilteredWord.class, instances);
	}
}
