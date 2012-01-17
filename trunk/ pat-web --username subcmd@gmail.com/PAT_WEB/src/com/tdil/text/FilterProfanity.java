package com.tdil.text;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Filter that replaces profanity.
 * 
 */
public class FilterProfanity {

	/**
	 * Array of all the bad words to filter.
	 */
	private Set<String> filterList = new HashSet<String>();

	/**
	 * Indicates if case of words should be ignored.
	 */
	private boolean ignoreCase = true;

	/**
	 * Creates a new filter not associated with a message. This is generally
	 * only useful for defining a template filter that other fitlers will be
	 * cloned from.
	 */
	public FilterProfanity() {
		super();
	}

	/**
	 * Filters out bad words.
	 */
	public String filterProfanity(String str) {
		StringBuffer ret_str = new StringBuffer(str.length());
		Pattern pattern = Pattern.compile("([a-z|A-Z]+)");
		Matcher matcher = pattern.matcher(str);
		int start = 0;
		while (matcher.find(start)) {
			ret_str.append(str.substring(start, matcher.start(1)));
			String word = matcher.group(1);
			if (check(word)) {
				ret_str.append(word);
			} else {
				ret_str.append(getMaskString(word.length()));
			}
			start = matcher.end(1);
		}
		return ret_str.toString();
	}

	private boolean check(String word) {
		if (ignoreCase) {
			return !filterList.contains(word.toLowerCase());
		} else {
			return !filterList.contains(word);
		}
	}

	private static String getMaskString(int len) {
		StringBuffer str = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			str.append("*");
		}
		return str.toString();
	}

	public void setFilterList(String[] filterList) {
		for (String s : filterList) {
			if (ignoreCase) {
				this.filterList.add(s.toLowerCase());
			} else {
				this.filterList.add(s);
			}
		}
	}
}