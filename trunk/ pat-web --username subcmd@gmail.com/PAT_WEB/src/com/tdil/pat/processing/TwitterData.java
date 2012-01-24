package com.tdil.pat.processing;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TwitterData {

	private static AtomicInteger size = new AtomicInteger(0);
	private static ConcurrentLinkedQueue<String> stream = new ConcurrentLinkedQueue<String>();
	
	public static void add(String string) {
		size.incrementAndGet();
		stream.add(string);
	}
	
	public static Integer getSize() {
		return size.get();
	}
	
	public static String remove() {
		String result = stream.poll();
		if (result != null) {
			size.decrementAndGet();
		}
		return result;
	}
}
