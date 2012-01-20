package com.tdil.pat.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tdil.cvs.CVSUtils;
import com.tdil.pat.text.FilterProfanity;

public class FilteredWord {

	private String word;
	
	private static String headers[] = {"word"};
	private static List<FilteredWord> instances = new ArrayList<FilteredWord>();
	
	private static FilterProfanity filterProfanity = new FilterProfanity();

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
		Set<String> filterList = new HashSet<String>();
		for (FilteredWord fw : instances) {
			filterList.add(fw.getWord());
		}
		filterProfanity.setFilterList(filterList);
	}

	public static FilterProfanity getFilterProfanity() {
		return filterProfanity;
	}
}
